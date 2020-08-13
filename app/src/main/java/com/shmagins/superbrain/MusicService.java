package com.shmagins.superbrain;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MusicService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (action != null) {
                switch (action) {
                    case START:
                        if (Build.VERSION.SDK_INT >= 26) {
                            String CHANNEL_ID = "super_brain_music_01";
                            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                                    "SuperBrain music channel",
                                    NotificationManager.IMPORTANCE_DEFAULT);

                            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

                            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                                    .setSmallIcon(R.mipmap.brain_icon)
                                    .setContentTitle("SuperBrain")
                                    .setContentText("Проигрывание фоновой музыки")
                                    .build();

                            Log.d("TAG", "onStartCommand: " + notification);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                startForeground(1, notification, 2);
                            } else {
                                startForeground(1, notification);
                            }

                            player = MediaPlayer.create(this, R.raw.game_theme);
                            player.setLooping(true);
                            if (!player.isPlaying()) {
                                player.start();
                            }

                            br = new MusicServiceBroadcastReceiver(player);
                            IntentFilter filter = new IntentFilter(BROADCAST);
                            LocalBroadcastManager.getInstance(this).registerReceiver(br, filter);
                        }

                        break;
                    case STOP:
                        stopForeground(true);
                        stopSelf();
                        break;
                }
            }
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(br);
        player.reset();
        player.release();
    }

    public static void pauseMusic (Context context) {
        resumeMusic(context, false);
    }

    public static void resumeMusic (Context context) {
        resumeMusic(context, true);
    }

    private static void resumeMusic(Context context, boolean resume) {
        Intent intent = new Intent(context, MusicServiceBroadcastReceiver.class);
        intent.setAction(MusicService.BROADCAST);
        intent.putExtra(MusicService.COMMAND, resume ? RESUME : PAUSE);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    public static class MusicServiceBroadcastReceiver extends BroadcastReceiver {

        public MusicServiceBroadcastReceiver(MediaPlayer player) {
            this.player = player;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            int command = intent.getIntExtra(COMMAND, 0);
            switch (command) {
                case PAUSE:
                    if (player.isPlaying()) {
                        player.pause();
                    }
                    break;
                case RESUME:
                    if (!player.isPlaying()) {
                        player.start();
                    }
                    break;
            }
        }

        private MediaPlayer player;
    }

    private BroadcastReceiver br;
    private MediaPlayer player;
    public static final String START = "START";
    public static final String STOP = "STOP";
    public static final String COMMAND = "COMMAND";
    public static final int PAUSE = 1;
    public static final int RESUME = 2;
    public static final String BROADCAST = "com.shmagins.superbrain.MusicServiceBroadcast";
}
