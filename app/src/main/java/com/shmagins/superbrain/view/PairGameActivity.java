package com.shmagins.superbrain.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Display;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.superbrain.PairGame;
import com.shmagins.superbrain.adapters.PairGameAdapter;
import com.shmagins.superbrain.ListGenerator;
import com.shmagins.superbrain.PairGameViewModel;
import com.shmagins.superbrain.R;
import com.shmagins.superbrain.SmileImages;
import com.shmagins.superbrain.databinding.ActivityPairGameBinding;

import java.util.List;

public class PairGameActivity extends AppCompatActivity {

    public static final String DIFFICULTY = "DIFFICULTY";
    private ActivityPairGameBinding binding;
    private PairGameViewModel viewModel;
    private PairGame game;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pair_game);
        viewModel = ViewModelProviders.of(this).get(PairGameViewModel.class);

        Intent intent = getIntent();
        int width = 0;
        int height = 0;
        if (intent != null) {
            width = (intent.getIntExtra(DIFFICULTY, 0) + 1) * 2;
        }

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int elementWidth = (size.x - 20) / width;
        int recyclerSize = size.y - 50 - binding.progressBar.getHeight();

        height = recyclerSize / elementWidth;
        if (width > 6){
            height--;
        }

        int multiplier =
                Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getApplication())
                        .getString("pref_pair_game_additional", "1"));

        List<Integer> elements = ListGenerator.generateResourceList(width * height * multiplier, SmileImages.images);
        game = viewModel.getGame(width * height, elements);
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
                case LOSE:
                    runOnUiThread(() -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage(R.string.game_lose_message)
                                .setTitle(R.string.game_lose_title)
                                .setIcon(R.drawable.animals_crab)
                                .setOnCancelListener(dialogInterface -> PairGameActivity.this.finish())
                                .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                                    Intent restartIntent = getIntent();
                                    finish();
                                    startActivity(restartIntent);
                                })
                                .setNegativeButton(R.string.no, (dialogInterface, i) -> PairGameActivity.this.finish());
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    });
                    break;
                case WIN:
                    runOnUiThread(() -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage(getString(R.string.game_win_message))
                                .setTitle(R.string.game_win_title)
                                .setIcon(R.drawable.animals_dog)
                                .setOnCancelListener(dialogInterface -> PairGameActivity.this.finish())
                                .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                                    Intent restartIntent = getIntent();
                                    finish();
                                    startActivity(restartIntent);
                                })
                                .setNegativeButton(R.string.no, (dialogInterface, i) -> PairGameActivity.this.finish());
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    });
                    break;
            }
        });
        binding.memoryRecycler.setAdapter(adapter);
        binding.memoryRecycler.setLayoutManager(new GridLayoutManager(this, width, RecyclerView.VERTICAL, false));

        binding.progressBar.setMax(game.getMaxProgress());
        binding.progressBar.setProgress(game.getProgress());
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

    public static Intent getStartIntent(Context context, int difficulty) {
        Intent intent = new Intent(context, PairGameActivity.class);
        intent.putExtra(DIFFICULTY, difficulty);
        return intent;
    }
}
