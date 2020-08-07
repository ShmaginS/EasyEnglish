package com.shmagins.superbrain.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.superbrain.BrainApplication;
import com.shmagins.superbrain.CalcGame;
import com.shmagins.superbrain.CalcViewModel;
import com.shmagins.superbrain.ViewModelFactory;
import com.shmagins.superbrain.adapters.CalcAdapter;
import com.shmagins.superbrain.R;
import com.shmagins.superbrain.RecyclerViewDisabler;
import com.shmagins.superbrain.databinding.ActivityCalculationBinding;

public class CalcGameActivity extends AppCompatActivity {
    private CalcViewModel viewModel;
    public static final String LVL = "LVL";
    private CalcGame game;
    private ActivityCalculationBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("CalcActivity", "onCreate: ");
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_calculation);
        viewModel = ViewModelProviders.of(this, new ViewModelFactory(getApplication())).get(CalcViewModel.class);

        int lvl = 11;
        Intent intent = getIntent();
        if (intent != null) {
            lvl = intent.getIntExtra(LVL, 11);
        }

        game = viewModel.createOrContinueCalcGame(lvl);
        CalcAdapter adapter = new CalcAdapter(game);

        int finalLvl = lvl;
        game.subscribe(integerEventPair -> {
            switch (integerEventPair.second) {
                case WIN:
                    int errors = game.getFailed().size();
                    int time = integerEventPair.first;
                    int stars = CalcGame.Rules.getStarsForResult(time, game.getCalculationCount(),errors);
                    Log.d("CalcGameActivity", "stars: " + stars + " errors: " + errors + " lvl: " + finalLvl);
                    ((BrainApplication) getApplication())
                            .getDatabaseComponent()
                            .getGameRepository()
                            .setGameStats(0, finalLvl, time, errors, stars);
                    if (stars > 0) {
                        ((BrainApplication) getApplication())
                                .getDatabaseComponent()
                                .getGameRepository()
                                .setGameStats(0, (finalLvl + 1) % 10 == 0 ? finalLvl + 2 : finalLvl + 1, 1000000, 0, 0);
                    }
                    runOnUiThread(() -> {
                        Intent i = CalcResultActivity.getStartIntent(this, integerEventPair.first);
                        startActivityForResult(i, CalcResultActivity.REQUEST);
                    });
                    break;
                case UPDATE_ALL:
                    adapter.notifyDataSetChanged();
                    break;
            }
        });


        binding.setRecycler(binding.recyclerView);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(binding.recyclerView);
        RecyclerViewDisabler disabler = new RecyclerViewDisabler(false);
        binding.recyclerView.addOnItemTouchListener(disabler);
        binding.setViewModel(viewModel);
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

    public static Intent getStartIntent(Context context, int lvl) {
        Intent intent = new Intent(context, CalcGameActivity.class);
        intent.putExtra(LVL, lvl);
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CalcResultActivity.REQUEST) {
            if (resultCode == RESULT_OK) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            } else {
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("happy", "onDestroy: ");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}


