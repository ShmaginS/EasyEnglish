package com.shmagins.easyenglish.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.disposables.Disposable;

import com.shmagins.easyenglish.CalculationViewModelFactory;
import com.shmagins.easyenglish.CalculationsViewModel;
import com.shmagins.easyenglish.R;
import com.shmagins.easyenglish.CalculationsAdapter;

public class CalculationFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_calculation, container, false);

        return fragmentView;
    }
}
