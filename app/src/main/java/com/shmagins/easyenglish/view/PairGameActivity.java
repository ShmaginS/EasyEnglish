package com.shmagins.easyenglish.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.easyenglish.AnimalImages;
import com.shmagins.easyenglish.AppViewModel;
import com.shmagins.easyenglish.PairGame;
import com.shmagins.easyenglish.ImageAdapter;
import com.shmagins.easyenglish.MatrixManager;
import com.shmagins.easyenglish.R;
import com.shmagins.easyenglish.databinding.ActivityMemoryBinding;

import java.util.List;

public class PairGameActivity extends AppCompatActivity {
    AppViewModel viewModel;

    public static final String WIDTH = "WIDTH";
    public static final String HEIGHT = "HEIGHT";
    public static final String ELEMENTS = "ELEMENTS";

    private ActivityMemoryBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_memory);

        Intent intent = getIntent();
        int width = 0;
        int height = 0;
        if (intent != null) {
            width = intent.getIntExtra(WIDTH, 0);
            height = intent.getIntExtra(HEIGHT, 0);
        }

        List<Integer> elements = MatrixManager.generateImageIndexMatrixWithDuplicates(width, height, AnimalImages.images.length);
        PairGame game = PairGame.getInstance();
        game.createGame(elements);
        ImageAdapter adapter = new ImageAdapter();
        adapter.setImages(
                MatrixManager.generateImageStates(elements, AnimalImages.images)
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
                                .setTitle(R.string.memory_game_lose)
                                .setIcon(R.drawable.crab)
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
                        builder.setMessage(R.string.memory_game_win)
                                .setTitle(R.string.game_win_title)
                                .setIcon(R.drawable.dog)
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
        binding.memoryRecycler.setBackgroundColor(Color.parseColor("#9eb3c2"));

        binding.progressBar.setMax(PairGame.START_TIME);
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

    public static Intent getStartIntent(Context context, int width, int height) {
        Intent intent = new Intent(context, PairGameActivity.class);
        intent.putExtra(WIDTH, width);
        intent.putExtra(HEIGHT, height);
        return intent;
    }
}
