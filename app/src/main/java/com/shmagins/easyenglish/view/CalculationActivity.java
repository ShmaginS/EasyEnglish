package com.shmagins.easyenglish.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.easyenglish.BrainApplication;
import com.shmagins.easyenglish.CalcGame;
import com.shmagins.easyenglish.CalcViewModel;
import com.shmagins.easyenglish.ViewModelFactory;
import com.shmagins.easyenglish.adapters.CalcAdapter;
import com.shmagins.easyenglish.R;
import com.shmagins.easyenglish.RecyclerViewDisabler;
import com.shmagins.easyenglish.databinding.ActivityCalculationBinding;

import io.reactivex.disposables.Disposable;

public class CalculationActivity extends AppCompatActivity {
    private Disposable disposable;
    private CalcViewModel viewModel;
    public static final String CALCULATION_COUNT = "CALCULATION_COUNT";
    private CalcGame game;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCalculationBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_calculation);
        viewModel = ViewModelProviders.of(this, new ViewModelFactory(getApplication())).get(CalcViewModel.class);

        game = viewModel.createOrContinueCalcGame();
        int limit = 0;
        Intent intent = getIntent();
        if (intent != null) {
            limit = intent.getIntExtra(CALCULATION_COUNT, 0);
        }
        disposable = viewModel.getAll(limit)
                .subscribe(game::setCalculations);

        game.subscribe(integerEventPair -> {
            switch (integerEventPair.second) {
                case WIN:
                    runOnUiThread(() -> {
                        Intent i = new Intent(this, ResultActivity.class);
                        startActivityForResult(i, ResultActivity.REQUEST);
                    });
                    break;
            }
        });

        CalcAdapter adapter = new CalcAdapter(game);
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

    public static Intent getStartIntent(Context context, int count){
        Intent intent = new Intent(context, CalculationActivity.class);
        intent.putExtra(CALCULATION_COUNT, count);
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ResultActivity.REQUEST){
            if (resultCode == RESULT_OK) {
                recreate();
            } else {
                finish();
            }
        }
    }
}


