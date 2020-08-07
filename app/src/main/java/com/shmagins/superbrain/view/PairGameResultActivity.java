package com.shmagins.superbrain.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.shmagins.superbrain.BrainApplication;
import com.shmagins.superbrain.R;
import com.shmagins.superbrain.SmileImages;
import com.shmagins.superbrain.databinding.ActivityPairGameResultBinding;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PairGameResultActivity extends AppCompatActivity {
    public static final String LEVEL = "LEVEL";
    public static final String STARS = "STARS";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPairGameResultBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_pair_game_result);
        BrainApplication app = (BrainApplication) getApplication();
        int level = 0;
        int stars = 0;
        Intent intent = getIntent();
        if (intent != null) {
            level = intent.getIntExtra(LEVEL, 0);
            stars = intent.getIntExtra(STARS, 0);
        }
        Log.d("PairGameResultActivity", "level: " + level + " stars: " + stars);
        binding.setStars(stars);
        app.getDatabaseComponent()
                .getGameRepository()
                .getPairGameLevel(level)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lvl -> {
                    if (lvl.inc != 0) {
                        binding.smilesOpened.setVisibility(View.VISIBLE);
                        binding.imageSmile1.setImageResource(SmileImages.images[lvl.num - 1]);
                        binding.imageSmile2.setImageResource(SmileImages.images[lvl.num]);
                        binding.imageSmile3.setImageResource(SmileImages.images[lvl.num + 1]);
                    }
                });
    }

    public static Intent getStartIntent(Context context, int level, int stars) {
        Intent intent = new Intent(context, PairGameResultActivity.class);
        intent.putExtra(LEVEL, level);
        intent.putExtra(STARS, stars);
        return intent;
    }

    public void onNextClick(View view) {
        this.finish();
    }
}
