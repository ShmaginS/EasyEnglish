package com.shmagins.superbrain;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import com.shmagins.superbrain.calcgame.CalcGame;
import com.shmagins.superbrain.di.DaggerDatabaseModule_DatabaseComponent;
import com.shmagins.superbrain.di.DatabaseModule;
import com.shmagins.superbrain.sound.DaggerSoundModule_SoundComponent;
import com.shmagins.superbrain.sound.SoundModule;
import com.shmagins.superbrain.sound.MusicService;

public class BrainApplication extends Application {
    private volatile CalcGame calcGame;
    private DatabaseModule.DatabaseComponent databaseComponent;
    private SoundModule.SoundComponent soundComponent;
    private SharedPreferences.OnSharedPreferenceChangeListener listener = (sharedPreferences, s) -> {
        if (s.equals("pref_enable_music")) {
            Intent intent = new Intent(this, MusicService.class);
            if (!sharedPreferences.getBoolean("pref_enable_music", false)) {
                stopService(intent);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    intent.setAction(MusicService.START);
                    startForegroundService(intent);
                } else {
                    intent.setAction(MusicService.START);
                    startService(intent);
                }
            }

        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        databaseComponent = DaggerDatabaseModule_DatabaseComponent.builder()
                .databaseModule(new DatabaseModule(this))
                .build();
        soundComponent = DaggerSoundModule_SoundComponent.builder()
                .soundModule(new SoundModule(this))
                .build();
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("pref_enable_music", false)) {
            Intent intent = new Intent(this, MusicService.class);
            intent.setAction(MusicService.START);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent);
            } else {
                startService(intent);
            }
        }
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(listener);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        soundComponent.getSoundRepository().getSoundManager().cleanup();
    }

    public DatabaseModule.DatabaseComponent getDatabaseComponent() {
        return databaseComponent;
    }

    public SoundModule.SoundComponent getSoundComponent() {
        return soundComponent;
    }

    public CalcGame createOrContinueCalcGame() {
        if (calcGame == null || calcGame.isFinished()) {
            synchronized (CalcGame.class) {
                if (calcGame == null || calcGame.isFinished()) {
                    calcGame = new CalcGame();
                }
            }
        }
        return calcGame;
    }

    public CalcGame getCalcGame() {
        return calcGame;
    }
}
