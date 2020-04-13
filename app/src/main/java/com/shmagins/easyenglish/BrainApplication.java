package com.shmagins.easyenglish;

import android.app.Application;

import com.shmagins.easyenglish.dagger.ApplicationModule;
import com.shmagins.easyenglish.dagger.DaggerApplicationModule_ApplicationComponent;
import com.shmagins.easyenglish.dagger.DatabaseModule;

public class BrainApplication extends Application {
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
}
