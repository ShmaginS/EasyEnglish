package com.shmagins.superbrain.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.superbrain.CalcGame;
import com.shmagins.superbrain.Expression;
import com.shmagins.superbrain.databinding.CalculationCardBinding;

public class CalcAdapter extends RecyclerView.Adapter {
    private CalcGame game;

    public CalcAdapter(CalcGame game){
        this.game = game;
    }

    public static class ExpressionViewHolder extends RecyclerView.ViewHolder {
        public CalculationCardBinding binding;

        ExpressionViewHolder(@NonNull CalculationCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Expression expression){
            binding.setExpression(expression);
            binding.answer.setText("");
            binding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CalculationCardBinding binding = CalculationCardBinding.inflate(inflater, parent, false);
        return new ExpressionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ExpressionViewHolder h = (ExpressionViewHolder) holder;
        h.bind(game.getExpression(position));
    }

    @Override
    public int getItemCount() {
        return game.getCalculationCount();
    }
}