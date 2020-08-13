package com.shmagins.superbrain.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.superbrain.BrainApplication;
import com.shmagins.superbrain.MemoryGame;
import com.shmagins.superbrain.MemoryGameViewModel;
import com.shmagins.superbrain.MusicService;
import com.shmagins.superbrain.R;
import com.shmagins.superbrain.SoundRepository;
import com.shmagins.superbrain.adapters.MemoryGameSelectionAdapter;
import com.shmagins.superbrain.adapters.MemoryGameVariantsAdapter;
import com.shmagins.superbrain.databinding.ActivitySmilesBinding;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MemoryGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySmilesBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_smiles);
        MemoryGameViewModel viewModel = ViewModelProviders.of(this).get(MemoryGameViewModel.class);

        Intent intent = getIntent();

        int lvl = 0;
        int usedImages = 0;
        int gameFieldWidth = 0;
        int gameFieldHeight = 0;

        if (intent != null) {
            lvl = intent.getIntExtra(LVL, 0);
            usedImages = intent.getIntExtra(USEDIMAGES, 0);
            gameFieldWidth = intent.getIntExtra(GAMEFIELDWIDTH, 0);
            gameFieldHeight = intent.getIntExtra(GAMEFIELDHEIGHT, 0);
        }

        boolean soundEnabled = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("pref_enable_sound", true);

        int finalLvl = lvl;
        int finalUsedImages = usedImages;
        int finalGameFieldWidth = gameFieldWidth;

        disposable = viewModel.getGame(usedImages, gameFieldWidth, gameFieldHeight)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(game -> {
                    this.game = game;

                    MemoryGameVariantsAdapter varAdapter = new MemoryGameVariantsAdapter(game);
                    MemoryGameSelectionAdapter selAdapter = new MemoryGameSelectionAdapter(game);

                    BrainApplication app = (BrainApplication) getApplication();
                    SoundRepository sound = app.getSoundComponent().getSoundRepository();

                    if (!game.isReady()) {
                        binding.recyclerVariants.setAlpha(0.0f);
                    }
                    binding.recyclerVariants.setAdapter(varAdapter);
                    binding.recyclerVariants.setLayoutManager(new GridLayoutManager(this, finalGameFieldWidth, RecyclerView.VERTICAL, false));
                    binding.recyclerAnswer.setAdapter(selAdapter);
                    binding.recyclerAnswer.setLayoutManager(new GridLayoutManager(this, finalUsedImages, RecyclerView.VERTICAL, false));
                    binding.tvLives.setText(String.valueOf(game.getElements().size()));

                    game.subscribe(integerGameEventPair -> {
                        Intent i;
                        switch (integerGameEventPair.second) {
                            case TIMER:
                                binding.imageSmile.clearAnimation();
                                Animation anim = AnimationUtils.loadAnimation(this, R.anim.fadein);
                                binding.imageSmile.setImageResource(integerGameEventPair.first);
                                binding.imageSmile.setAnimation(anim);
                                anim.start();
                                break;
                            case UPDATE:
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        runOnUiThread(() -> {
                                            binding.imageSmile.setVisibility(View.GONE);
                                            binding.questionPrompt.setVisibility(View.GONE);
                                            binding.answerPrompt.setVisibility(View.VISIBLE);
                                            binding.recyclerVariants.setVisibility(View.VISIBLE);
                                            ValueAnimator animation = ValueAnimator.ofFloat(0f, 1.0f);
                                            animation.setDuration(500);
                                            animation.addUpdateListener(f -> binding.recyclerVariants.setAlpha((float) f.getAnimatedValue()));
                                            animation.start();
                                        });
                                    }
                                }, 1000);
                                break;
                            case FAIL:
                                binding.tvLives.setText(String.valueOf(game.getElements().size() - game.getErrors()));
                                break;
                            case SELECT:
                                selAdapter.notifyItemChanged(game.getSelection().size() - 1);
                                RecyclerView.LayoutManager lm = binding.recyclerAnswer.getLayoutManager();
                                if (lm != null) {
                                    lm.scrollToPosition(game.getSelection().size());
                                }
                                break;
                            case LOSE:
                            case WIN:
                                int time = game.stopGame();
                                int total = game.getElements().size();
                                int errors = game.getErrors();
                                int stars = MemoryGame.Rules.getStarsForResult(total, errors);
                                if (stars > 0) {
                                    ((BrainApplication) getApplication())
                                            .getDatabaseComponent()
                                            .getGameRepository()
                                            .setGameStats(2, finalLvl, time, errors, stars);
                                    ((BrainApplication) getApplication())
                                            .getDatabaseComponent()
                                            .getGameRepository()
                                            .setGameStats(2, finalLvl + 1, 1000000, 0, 0);
                                }
                                i = MemoryGameResultActivity.getStartIntent(this, stars, time, errors);
                                startActivity(i);
                                finish();
                                break;
                            case SOUND:
                                if (soundEnabled) {
                                    switch (integerGameEventPair.first) {
                                        case 0:
                                            sound.playGoodSound();
                                            break;
                                        case 1:
                                            sound.playFailSound();
                                            break;
                                    }
                                }
                                break;
                        }
                    });
                    game.startGame();
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MusicService.resumeMusic(this);
        if (game != null) {
            game.startGame();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MusicService.pauseMusic(this);
        if (game != null) {
            game.pauseGame();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public static Intent getStartIntent(Context context, int lvl, int usedImages,
                                        int gameFieldWidth, int gameFieldHeight) {
        Intent intent = new Intent(context, MemoryGameActivity.class);
        intent.putExtra(LVL, lvl);
        intent.putExtra(USEDIMAGES, usedImages);
        intent.putExtra(GAMEFIELDWIDTH, gameFieldWidth);
        intent.putExtra(GAMEFIELDHEIGHT, gameFieldHeight);
        return intent;
    }

    private volatile MemoryGame game;
    private Disposable disposable;
    public static final String LVL = "LVL";
    public static final String USEDIMAGES = "USEDIMAGES";
    public static final String GAMEFIELDWIDTH = "GAMEFIELDWIDTH";
    public static final String GAMEFIELDHEIGHT = "GAMEFIELDHEIGHT";
}
