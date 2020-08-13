package com.shmagins.superbrain.dagger;

import android.content.Context;

import com.shmagins.superbrain.SoundManager;
import com.shmagins.superbrain.SoundRepository;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

@Module
public class SoundModule {
    public SoundModule(Context appContext) {
        this.appContext = appContext;
    }

    @Provides
    @Singleton
    SoundManager provideSoundManager() {
        return new SoundManager(appContext);
    }

    @Provides
    SoundRepository provideSoundRepository(SoundManager sm) {
        return new SoundRepository(sm);
    }

    @Component (modules = SoundModule.class)
    @Singleton
    public interface SoundComponent{
        SoundRepository getSoundRepository();
    }

    private Context appContext;
}
