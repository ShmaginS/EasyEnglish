package com.shmagins.easyenglish.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.easyenglish.CalcGame;
import com.shmagins.easyenglish.databinding.CalculationCardBinding;
import com.shmagins.easyenglish.db.Calculation;

import java.util.ArrayList;
import java.util.List;

public class CalcAdapter extends RecyclerView.Adapter {
    private CalcGame game;

    public CalcAdapter(CalcGame game){
        this.game = game;
    }

    public class CalculationsViewHolder extends RecyclerView.ViewHolder {
        public CalculationCardBinding binding;

        CalculationsViewHolder(@NonNull CalculationCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("WrongConstant")
        public void bind(Calculation calculation){
            binding.setCalculation(calculation);
            binding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CalculationCardBinding binding = CalculationCardBinding.inflate(inflater, parent, false);
        return new CalculationsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CalculationsViewHolder h = (CalculationsViewHolder) holder;
        h.bind(game.getCalculation(position));
    }

    @Override
    public int getItemCount() {
        return game.getCalculationCount();
    }
}