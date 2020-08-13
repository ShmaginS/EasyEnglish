package com.shmagins.superbrain.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.shmagins.superbrain.BrainApplication;
import com.shmagins.superbrain.CalcGame;
import com.shmagins.superbrain.MusicService;
import com.shmagins.superbrain.R;
import com.shmagins.superbrain.databinding.ActivityCalcGameResultBinding;

public class CalcGameResultActivity extends AppCompatActivity {
    private static final String MILLISECONDS = "MILLISECONDS";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCalcGameResultBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_calc_game_result);
        CalcGame game = ((BrainApplication)getApplication()).getCalcGame();
        int milliseconds = getIntent().getIntExtra(MILLISECONDS, 0);
        int seconds = milliseconds / 1000;
        int minutes = seconds / 60;
        seconds = seconds % 60;
        int rightCount = game.rightCount();
        int wrongCount = game.wrongCount();
        int totalCount = rightCount + wrongCount;
        binding.setStars(CalcGame.Rules.getStarsForResult(milliseconds, totalCount, wrongCount));
        binding.gameSummary.setText(
                getString(R.string.calc_game_summary, rightCount, totalCount, minutes, seconds)
        );
    }


    public static Intent getStartIntent(Context context, int milliseconds) {
        Intent intent = new Intent(context, CalcGameResultActivity.class);
        intent.putExtra(MILLISECONDS, milliseconds);
        return intent;
    }

    public void onNextClick(View view) {
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MusicService.pauseMusic(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MusicService.resumeMusic(this);
    }
}
