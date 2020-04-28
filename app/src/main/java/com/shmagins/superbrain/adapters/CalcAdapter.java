package com.shmagins.superbrain.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.superbrain.CalcGame;
import com.shmagins.superbrain.databinding.CalculationCardBinding;
import com.shmagins.superbrain.Calculation;

public class CalcAdapter extends RecyclerView.Adapter {
    private CalcGame game;

    public CalcAdapter(CalcGame game){
        this.game = game;
    }

    public static class CalculationsViewHolder extends RecyclerView.ViewHolder {
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