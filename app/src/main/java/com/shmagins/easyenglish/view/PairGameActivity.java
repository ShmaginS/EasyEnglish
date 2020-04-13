package com.shmagins.easyenglish.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.easyenglish.AnimalImages;
import com.shmagins.easyenglish.PairGame;
import com.shmagins.easyenglish.ImageAdapter;
import com.shmagins.easyenglish.MatrixManager;
import com.shmagins.easyenglish.R;
import com.shmagins.easyenglish.SmileImages;
import com.shmagins.easyenglish.databinding.ActivityPairGameBinding;

import java.util.List;

public class PairGameActivity extends AppCompatActivity {

    public static final String DIFFICULTY = "DIFFICULTY";

    private ActivityPairGameBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pair_game);

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


        List<Integer> elements = MatrixManager.generateImageIndexMatrixWithDuplicates(width, height, SmileImages.images.length);
        PairGame game = PairGame.getInstance();
        game.createGame(elements);
        ImageAdapter adapter = new ImageAdapter();
        adapter.setImages(
                MatrixManager.generateImageStates(elements, SmileImages.images)
        );
        PairGame.getInstance().subscribe(integerEventPair -> {
            switch (integerEventPair.second) {
                case SELECT:
                case DESELECT:
                    adapter.notifyItemChanged(integerEventPair.first);
                    break;
                case TIMER:
                    binding.progressBar.setProgress(PairGame.getInstance().getTime());
                    break;
                case LOSE:
                    runOnUiThread(() -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage(R.string.game_lose_message)
                                .setTitle(R.string.game_lose_title)
                                .setIcon(R.drawable.animals_crab)
                                .setOnCancelListener(dialogInterface -> PairGameActivity.this.finish())
                                .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                                    PairGameActivity.this.recreate();
                                })
                                .setNegativeButton(R.string.no, (dialogInterface, i) -> {
                                    PairGameActivity.this.finish();
                                });
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
                                    PairGameActivity.this.recreate();
                                })
                                .setNegativeButton(R.string.no, (dialogInterface, i) -> {
                                    PairGameActivity.this.finish();
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    });
                    break;
            }
        });
        binding.memoryRecycler.setAdapter(adapter);
        binding.memoryRecycler.setLayoutManager(new GridLayoutManager(this, width, RecyclerView.VERTICAL, false));

        binding.progressBar.setMax(PairGame.getInstance().getStartTime());
        binding.progressBar.setProgress(PairGame.getInstance().getTime());
    }

    @Override
    protected void onResume() {
        super.onResume();
        PairGame.getInstance().startGame();
    }

    @Override
    protected void onPause() {
        super.onPause();
        PairGame.getInstance().pauseGame();
    }

    public static Intent getStartIntent(Context context, int difficulty) {
        Intent intent = new Intent(context, PairGameActivity.class);
        intent.putExtra(DIFFICULTY, difficulty);
        return intent;
    }
}
