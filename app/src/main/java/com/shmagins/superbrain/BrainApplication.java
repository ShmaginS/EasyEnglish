package com.shmagins.superbrain;

import android.app.Application;
import android.util.Log;

import com.shmagins.superbrain.dagger.ApplicationModule;
import com.shmagins.superbrain.dagger.DaggerApplicationModule_ApplicationComponent;
import com.shmagins.superbrain.dagger.DatabaseModule;

public class BrainApplication extends Application {
    private volatile CalcGame calcGame;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationModule_ApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .databaseModule(new DatabaseModule(this))
                .build();
        applicationComponent.inject(this);
    }

    public ApplicationModule.ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    private ApplicationModule.ApplicationComponent applicationComponent;

    public CalcGame createOrContinueCalcGame() {
        Log.d("happy", "createOrContinueCalcGame");
        if (calcGame == null || calcGame.isFinished()) {
            synchronized (CalcGame.class) {
                if (calcGame == null || calcGame.isFinished()) {
                    Log.d("happy", "createOrContinueCalcGame creating new game");
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
