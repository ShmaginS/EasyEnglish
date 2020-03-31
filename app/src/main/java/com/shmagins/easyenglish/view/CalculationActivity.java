package com.shmagins.easyenglish.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.easyenglish.AppViewModel;
import com.shmagins.easyenglish.AppViewModelFactory;
import com.shmagins.easyenglish.CalcAdapter;
import com.shmagins.easyenglish.R;
import com.shmagins.easyenglish.RecyclerViewDisabler;
import com.shmagins.easyenglish.databinding.ActivityCalculationBinding;

import io.reactivex.disposables.Disposable;

public class CalculationActivity extends AppCompatActivity {
    private Disposable disposable;
    private AppViewModel viewModel;
    private long milliseconds = 0;
    private long startTime = 0;
    public static final String CALCULATION_COUNT = "CALCULATION_COUNT";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCalculationBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_calculation);
        CalcAdapter adapter = new CalcAdapter();
        binding.setRecycler(binding.recyclerView);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(binding.recyclerView);
        RecyclerViewDisabler disabler = new RecyclerViewDisabler(false);
        binding.recyclerView.addOnItemTouchListener(disabler);
        viewModel = ViewModelProviders.of(this, new AppViewModelFactory(getApplication())).get(AppViewModel.class);
        binding.setViewModel(viewModel);
        Intent intent = getIntent();
        int limit = 0;
        if (intent != null) {
            limit = intent.getIntExtra(CALCULATION_COUNT, 0);
        }
        disposable = viewModel.getAll(limit)
                .subscribe(adapter::setCalculations);
        startTime = SystemClock.elapsedRealtime();
    }

    public static Intent getStartIntent(Context context, int count){
        Intent intent = new Intent(context, CalculationActivity.class);
        intent.putExtra(CALCULATION_COUNT, count);
        return intent;
    }

}


