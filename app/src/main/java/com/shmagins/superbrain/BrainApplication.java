package com.shmagins.superbrain;

import android.app.Application;
import android.util.Log;

import com.shmagins.superbrain.dagger.DaggerDatabaseModule_DatabaseComponent;
import com.shmagins.superbrain.dagger.DatabaseModule;

public class BrainApplication extends Application {
    private volatile CalcGame calcGame;
    private DatabaseModule.DatabaseComponent databaseComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        databaseComponent = DaggerDatabaseModule_DatabaseComponent.builder()
                .databaseModule(new DatabaseModule(this))
                .build();
    }

    public DatabaseModule.DatabaseComponent getDatabaseComponent() {
        return databaseComponent;
    }

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
