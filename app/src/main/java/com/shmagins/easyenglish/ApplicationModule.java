package com.shmagins.easyenglish;

import android.app.Application;
import android.content.Context;

import com.shmagins.easyenglish.view.CalculationFragment;

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
    public static interface ApplicationComponent {
        Context getContext();

        Application getApplication();

        void inject(WordsApplication wordsApplication);

        void inject(WordsViewModel wordsViewModel);

        void inject(CalculationFragment calculationFragment);
    }
}
