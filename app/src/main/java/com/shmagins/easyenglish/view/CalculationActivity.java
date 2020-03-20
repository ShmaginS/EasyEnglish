package com.shmagins.easyenglish.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.easyenglish.CalculationViewModelFactory;
import com.shmagins.easyenglish.CalculationsAdapter;
import com.shmagins.easyenglish.CalculationsViewModel;
import com.shmagins.easyenglish.R;
import com.shmagins.easyenglish.databinding.ActivityCalculationBinding;

import io.reactivex.disposables.Disposable;

public class CalculationActivity extends AppCompatActivity {
    private Disposable disposable;
    CalculationsViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCalculationBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_calculation);
        CalculationsAdapter adapter = new CalculationsAdapter();
        binding.setRecycler(binding.recyclerView);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(binding.recyclerView);
        viewModel = ViewModelProviders.of(this, new CalculationViewModelFactory(getApplication())).get(CalculationsViewModel.class);
        binding.setViewModel(viewModel);
        disposable = viewModel.getAll()
                .subscribe(adapter::setCalculations);
    }

    public static Intent getStartIntent(Context context, int count){
        Intent intent = new Intent(context, CalculationActivity.class);
        intent.putExtra("CALCULATION_COUNT", count);
        return intent;
    }

}
