package com.shmagins.easyenglish;

import android.app.Application;
import android.content.Context;

import com.shmagins.easyenglish.view.WordsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        DatabaseModule.class
})
public interface ApplicationComponent {
    Context getContext();

    Application getApplication();

    void inject(WordsApplication wordsApplication);

    void inject(WordsViewModel wordsViewModel);

    void inject(WordsFragment wordsFragment);
}
