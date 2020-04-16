package com.shmagins.easyenglish;

import android.app.Application;

import com.shmagins.easyenglish.dagger.ApplicationModule;
import com.shmagins.easyenglish.dagger.DaggerApplicationModule_ApplicationComponent;
import com.shmagins.easyenglish.dagger.DatabaseModule;

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
