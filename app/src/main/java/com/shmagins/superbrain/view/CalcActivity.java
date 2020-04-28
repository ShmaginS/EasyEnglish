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

import com.shmagins.superbrain.CalcGame;
import com.shmagins.superbrain.CalcViewModel;
import com.shmagins.superbrain.ViewModelFactory;
import com.shmagins.superbrain.adapters.CalcAdapter;
import com.shmagins.superbrain.R;
import com.shmagins.superbrain.RecyclerViewDisabler;
import com.shmagins.superbrain.databinding.ActivityCalculationBinding;

public class CalcActivity extends AppCompatActivity {
    private CalcViewModel viewModel;
    public static final String CALCULATION_COUNT = "CALCULATION_COUNT";
    private CalcGame game;
    private ActivityCalculationBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("happy", "onCreate: ");
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_calculation);
        viewModel = ViewModelProviders.of(this, new ViewModelFactory(getApplication())).get(CalcViewModel.class);


        int limit = 10;
        Intent intent = getIntent();
        if (intent != null) {
            limit = intent.getIntExtra(CALCULATION_COUNT, 10);
        }

        game = viewModel.createOrContinueCalcGame(limit);
        CalcAdapter adapter = new CalcAdapter(game);

        game.subscribe(integerEventPair -> {
            switch (integerEventPair.second) {
                case WIN:
                    runOnUiThread(() -> {
                        Intent i = new Intent(this, ResultActivity.class);
                        startActivityForResult(i, ResultActivity.REQUEST);
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

    public static Intent getStartIntent(Context context, int count){
        Intent intent = new Intent(context, CalcActivity.class);
        intent.putExtra(CALCULATION_COUNT, count);
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ResultActivity.REQUEST){
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


