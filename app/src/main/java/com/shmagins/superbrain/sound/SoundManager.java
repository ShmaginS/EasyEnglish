package com.shmagins.superbrain.sound;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;

public class SoundManager {

    void playSoundFromResource(int resId) {
        Uri mediaPath = Uri.parse("android.resource://" + context.getPackageName() + "/" + resId);
        Log.d("SoundManager", mediaPath.toString());
        try {
            player.reset();
            player.setDataSource(context, mediaPath);
            player.prepareAsync();
            player.setOnPreparedListener(MediaPlayer::start);
        } catch (IOException e) {
            Log.d("SoundManager", "playSoundFromResource failed with " + e);
        } catch (IllegalStateException e) {
            Log.d("SoundManager", "playSoundFromResource failed with " + e);
        }
    }

    public SoundManager(Context context) {
        this.context = context;
        player = new MediaPlayer();
    }

    public void cleanup() {
        if (player != null) {
            player.reset();
            player.release();
        }
    }

    private Context context;
    private MediaPlayer player;
}
