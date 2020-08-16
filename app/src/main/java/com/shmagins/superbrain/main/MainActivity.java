package com.shmagins.superbrain.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;

import com.shmagins.superbrain.R;
import com.shmagins.superbrain.calcgame.CalcGameLevelsActivity;
import com.shmagins.superbrain.databinding.FragmentMainBinding;
import com.shmagins.superbrain.memorygame.MemoryGameLevelsActivity;
import com.shmagins.superbrain.pairgame.PairGameLevelsActivity;
import com.shmagins.superbrain.settings.SettingsActivity;
import com.shmagins.superbrain.sound.MusicService;

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
