package com.shmagins.superbrain.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.TypedArrayUtils;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.superbrain.AnimalImages;
import com.shmagins.superbrain.GameEvent;
import com.shmagins.superbrain.ListGenerator;
import com.shmagins.superbrain.MemoryGame;
import com.shmagins.superbrain.R;
import com.shmagins.superbrain.adapters.MemoryGameSelectionAdapter;
import com.shmagins.superbrain.adapters.MemoryGameVariantsAdapter;
import com.shmagins.superbrain.databinding.ActivitySmilesBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class MemoryGameActivity extends AppCompatActivity {
    private MemoryGame game;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySmilesBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_smiles);

        List<Integer> elements = ListGenerator.generateImageList(
                ListGenerator.generateImageIndexList(3, AnimalImages.images.length),
                AnimalImages.images);
        List<Integer> animals = toArrayList(AnimalImages.images, Integer.class);
        List<Integer> variants = ListGenerator.appendAndShuffle(elements, animals, AnimalImages.images.length);

        game = new MemoryGame();
        game.setElements(elements);
        game.setVariants(variants);

        MemoryGameVariantsAdapter varAdapter = new MemoryGameVariantsAdapter(game);
        MemoryGameSelectionAdapter selAdapter = new MemoryGameSelectionAdapter(game);

        binding.recyclerVariants.setAlpha(0.0f);
        binding.recyclerVariants.setAdapter(varAdapter);
        binding.recyclerVariants.setLayoutManager(new GridLayoutManager(this, 5, RecyclerView.VERTICAL, false));
        binding.recyclerAnswer.setAdapter(selAdapter);
        binding.recyclerAnswer.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        game.subscribe(integerGameEventPair -> {
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
                case SELECT:
                    Log.d("happy", "onCreate: " + game.getSelection());
                    selAdapter.notifyItemChanged(game.getSelection().size() - 1);
                    RecyclerView.LayoutManager lm = binding.recyclerAnswer.getLayoutManager();
                    if (lm != null) {
                        lm.scrollToPosition(game.getSelection().size());
                    }
                    break;
                case LOSE:
                    AlertDialog.Builder loseBuilder = new AlertDialog.Builder(this);
                    loseBuilder.setMessage(R.string.game_lose_message)
                            .setTitle(R.string.game_lose_title)
                            .setIcon(R.drawable.animals_crab)
                            .setOnCancelListener(dialogInterface -> MemoryGameActivity.this.finish())
                            .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                                Intent restartIntent = getIntent();
                                finish();
                                startActivity(restartIntent);
                            })
                            .setNegativeButton(R.string.no, (dialogInterface, i) -> {
                                dialogInterface.dismiss();
                                MemoryGameActivity.this.finish();
                            });
                    game.stopGame();
                    AlertDialog loseDialog = loseBuilder.create();
                    loseDialog.show();
                    break;
                case WIN:
                    AlertDialog.Builder winBuilder = new AlertDialog.Builder(this);
                    winBuilder.setMessage(getString(R.string.game_win_message))
                            .setTitle(R.string.game_win_title)
                            .setIcon(R.drawable.animals_dog)
                            .setOnCancelListener(dialogInterface -> MemoryGameActivity.this.finish())
                            .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                                Intent restartIntent = getIntent();
                                finish();
                                startActivity(restartIntent);
                            })
                            .setNegativeButton(R.string.no, (dialogInterface, i) -> {
                                dialogInterface.dismiss();
                                MemoryGameActivity.this.finish();
                            });
                    game.stopGame();
                    AlertDialog winDialog = winBuilder.create();
                    winDialog.show();
                    break;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        game.startGame();
    }

    @Override
    protected void onPause() {
        super.onPause();
        game.pauseGame();
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MemoryGameActivity.class);
    }

    <T> ArrayList<T> toArrayList(Object o, Class<T> type) {
        ArrayList<T> objects = new ArrayList<>();
        for (int i = 0; i < Array.getLength(o); i++) {
            objects.add((T) Array.get(o, i));
        }
        return objects;
    }
}
