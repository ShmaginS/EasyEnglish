package com.shmagins.easyenglish.dagger;

import android.app.Application;
import android.content.Context;

import com.shmagins.easyenglish.CalcViewModel;
import com.shmagins.easyenglish.BrainApplication;
import com.shmagins.easyenglish.view.MainFragment;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return application;
    }

    @Singleton
    @Provides
    Application provideApplication() {
        return application;
    }

    @Singleton
    @Component(modules = {
            ApplicationModule.class,
            DatabaseModule.class
    })
    public interface ApplicationComponent {
        Context getContext();

        Application getApplication();

        void inject(BrainApplication wordsApplication);

        void inject(CalcViewModel calcViewModel);

        void inject(MainFragment mainFragment);
    }
}
