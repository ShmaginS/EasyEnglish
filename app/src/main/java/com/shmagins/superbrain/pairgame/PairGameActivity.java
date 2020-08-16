package com.shmagins.superbrain.pairgame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.superbrain.BrainApplication;
import com.shmagins.superbrain.sound.MusicService;
import com.shmagins.superbrain.sound.SoundRepository;
import com.shmagins.superbrain.R;
import com.shmagins.superbrain.databinding.ActivityPairGameBinding;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PairGameActivity extends AppCompatActivity {

    public static final String LVL = "LVL";
    public static final String WIDTH = "WIDTH";
    public static final String HEIGHT = "HEIGHT";
    public static final String SCREENS = "SCREENS";
    public static final String INCREMENT = "INCREMENT";
    public static final String COUNT = "COUNT";
    private ActivityPairGameBinding binding;
    private PairGame game;
    private Disposable disposable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pair_game);
        PairGameViewModel viewModel = ViewModelProviders.of(this).get(PairGameViewModel.class);
        BrainApplication app = (BrainApplication) getApplication();
        SoundRepository sound = app.getSoundComponent().getSoundRepository();

        boolean soundEnabled = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("pref_enable_sound", true);

        Intent intent = getIntent();
        int lvl = 0;
        int width = 0;
        int height = 0;
        int screens = 0;
        int increment = 0;
        int count = 0;
        if (intent != null) {
            lvl = intent.getIntExtra(LVL, 0);
            width = intent.getIntExtra(WIDTH, 0);
            height = intent.getIntExtra(HEIGHT, 0);
            screens = intent.getIntExtra(SCREENS, 0);
            increment = intent.getIntExtra(INCREMENT, 0);
            count = intent.getIntExtra(COUNT, 0);
        }
        int finalLvl = lvl;
        int finalWidth = width;

        disposable = viewModel.getGame(width, height, screens, count)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(game -> {
                    this.game = game;
                    PairGameAdapter adapter = new PairGameAdapter(game);
                    game.subscribe(integerEventPair -> {
                        switch (integerEventPair.second) {
                            case SELECT:
                            case DESELECT:
                                adapter.notifyItemChanged(integerEventPair.first);
                                break;
                            case TIMER:
                                binding.progressBar.setProgress(game.getProgress());
                                break;
                            case WIN:
                                int errors = game.getFailed();
                                int time = integerEventPair.first;
                                int total = game.getTotalCount();
                                int stars = PairGame.Rules.getStarsForResult(total, errors, time);
                                ((BrainApplication) getApplication())
                                        .getDatabaseComponent()
                                        .getGameRepository()
                                        .setGameStats(1, finalLvl, time, errors, stars);
                                if (stars > 0) {
                                    ((BrainApplication) getApplication())
                                            .getDatabaseComponent()
                                            .getGameRepository()
                                            .setGameStats(1, finalLvl + 1, 1000000, 0, 0);
                                }
                                Intent i = PairGameResultActivity.getStartIntent(this, finalLvl, stars);
                                startActivity(i);
                                finish();
                                break;
                            case SOUND:
                                if (soundEnabled) {
                                    switch (integerEventPair.first) {
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
                    binding.memoryRecycler.setAdapter(adapter);
                    binding.memoryRecycler.setLayoutManager(new GridLayoutManager(this, finalWidth, RecyclerView.VERTICAL, false));

                    binding.progressBar.setMax(game.getMaxProgress());
                    binding.progressBar.setProgress(game.getProgress());

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
        MusicService.pauseMusic(this);
        super.onPause();
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

    public static Intent getStartIntent(Context context, int lvl, int width, int height, int screens, int increment, int count) {
        Intent intent = new Intent(context, PairGameActivity.class);
        intent.putExtra(LVL, lvl);
        intent.putExtra(WIDTH, width);
        intent.putExtra(HEIGHT, height);
        intent.putExtra(SCREENS, screens);
        intent.putExtra(INCREMENT, increment);
        intent.putExtra(COUNT, count);
        return intent;
    }
}
