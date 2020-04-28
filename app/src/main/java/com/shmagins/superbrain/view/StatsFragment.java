package com.shmagins.superbrain.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.shmagins.superbrain.R;
import com.shmagins.superbrain.databinding.FragmentStatsBinding;

public class StatsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentStatsBinding binding = FragmentStatsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
