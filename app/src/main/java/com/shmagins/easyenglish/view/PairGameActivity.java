package com.shmagins.easyenglish.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.easyenglish.PairGame;
import com.shmagins.easyenglish.adapters.PairGameAdapter;
import com.shmagins.easyenglish.MatrixManager;
import com.shmagins.easyenglish.PairGameViewModel;
import com.shmagins.easyenglish.R;
import com.shmagins.easyenglish.SmileImages;
import com.shmagins.easyenglish.databinding.ActivityPairGameBinding;

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


        List<Integer> elements = MatrixManager.generateResourceList(width * height, SmileImages.images);
        game = viewModel.getGame(width * height, elements);
        PairGameAdapter adapter = new PairGameAdapter(game);
        game.subscribe(integerEventPair -> {
            switch (integerEventPair.second) {
                case SELECT:
                case DESELECT:
                    adapter.notifyItemChanged(integerEventPair.first);
                    break;
                case TIMER:
                    binding.progressBar.setProgress(game.getTime());
                    break;
                case LOSE:
                    runOnUiThread(() -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage(R.string.game_lose_message)
                                .setTitle(R.string.game_lose_title)
                                .setIcon(R.drawable.animals_crab)
                                .setOnCancelListener(dialogInterface -> PairGameActivity.this.finish())
                                .setPositiveButton(R.string.yes, (dialogInterface, i) -> PairGameActivity.this.recreate())
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
                                .setPositiveButton(R.string.yes, (dialogInterface, i) -> PairGameActivity.this.recreate())
                                .setNegativeButton(R.string.no, (dialogInterface, i) -> PairGameActivity.this.finish());
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    });
                    break;
            }
        });
        binding.memoryRecycler.setAdapter(adapter);
        binding.memoryRecycler.setLayoutManager(new GridLayoutManager(this, width, RecyclerView.VERTICAL, false));

        binding.progressBar.setMax(game.getStartTime());
        binding.progressBar.setProgress(game.getTime());
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
