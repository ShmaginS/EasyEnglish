package com.shmagins.superbrain.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.JobIntentService;
import androidx.databinding.DataBindingUtil;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.content.MutableContextWrapper;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shmagins.superbrain.BrainApplication;
import com.shmagins.superbrain.MusicService;
import com.shmagins.superbrain.R;
import com.shmagins.superbrain.databinding.FragmentMainBinding;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentMainBinding binding = DataBindingUtil.setContentView(this, R.layout.fragment_main);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    public void onStartCalculationClick(View view) {
        Intent intent = CalcGameLevelsActivity.getStartIntent(this);
        startActivity(intent);
    }

    public void onStartMemoryClick(View view) {
        Intent intent = PairGameLevelsActivity.getStartIntent(this);
        startActivity(intent);
    }

    public void onShowSettingsClick(View view) {
        Intent intent = SettingsActivity.getStartIntent(this);
        startActivity(intent);
    }

    public void onStartSmilesClick(View view){
        Intent intent = MemoryGameLevelsActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MusicService.resumeMusic(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MusicService.pauseMusic(this);
    }
}
