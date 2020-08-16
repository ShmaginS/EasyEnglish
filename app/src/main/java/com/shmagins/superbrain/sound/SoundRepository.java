package com.shmagins.superbrain.sound;

import com.shmagins.superbrain.R;

public class SoundRepository {
    public SoundManager getSoundManager() {
        return soundManager;
    }

    SoundManager soundManager;

    public SoundRepository(SoundManager soundManager) {
        this.soundManager = soundManager;
    }

    public void playGoodSound(){
        soundManager.playSoundFromResource(R.raw.pop);
    }

    public void playFailSound(){
        soundManager.playSoundFromResource(R.raw.zip);
    }

    public void playLevelCompleteSound(){
        soundManager.playSoundFromResource(R.raw.chord);
    }
}