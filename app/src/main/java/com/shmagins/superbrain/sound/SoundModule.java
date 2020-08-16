package com.shmagins.superbrain.sound;

import android.content.Context;

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
