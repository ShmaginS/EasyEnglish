package com.shmagins.superbrain.memorygame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.shmagins.superbrain.BrainApplication;
import com.shmagins.superbrain.sound.MusicService;
import com.shmagins.superbrain.R;
import com.shmagins.superbrain.sound.SoundRepository;
import com.shmagins.superbrain.databinding.ActivityMemoryGameResultBinding;

public class MemoryGameResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMemoryGameResultBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_memory_game_result);
        BrainApplication app = (BrainApplication) getApplication();
        int stars = 0;
        int errors = 0;
        int time = 0;

        Intent intent = getIntent();
        if (intent != null) {
            stars = intent.getIntExtra(STARS, 0);
            time = intent.getIntExtra(TIME, 0);
            errors = intent.getIntExtra(ERRORS, 0);
        }

        int seconds = time / 1000;
        int minutes = seconds / 60;
        seconds = seconds % 60;

        binding.setStars(stars);
        SoundRepository sound = app.getSoundComponent().getSoundRepository();

        boolean soundEnabled = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("pref_enable_sound", true);

        if (soundEnabled) {
            sound.playLevelCompleteSound();
        }

        binding.gameSummary.setText(getResources().getString(R.string.memory_game_summary, errors, minutes, seconds));
    }

    public static Intent getStartIntent(Context context, int stars, int time, int errors) {
        Intent intent = new Intent(context, MemoryGameResultActivity.class);
        intent.putExtra(STARS, stars);
        intent.putExtra(TIME, time);
        intent.putExtra(ERRORS, errors);
        return intent;
    }

    public void onNextClick(View view) {
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MusicService.resumeMusic(this);
    }

    @Override
    protected void onPause() {
        MusicService.pauseMusic(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static final String STARS = "STARS";
    public static final String TIME = "TIME";
    public static final String ERRORS = "ERRORS";
}
