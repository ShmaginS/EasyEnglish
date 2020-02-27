package com.shmagins.easyenglish;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.easyenglish.databinding.CalculationCardBinding;
import com.shmagins.easyenglish.db.Calculation;

import java.util.ArrayList;
import java.util.List;

public class CalculationsAdapter extends RecyclerView.Adapter {
    private List<Calculation> calculations;

    {
        calculations = new ArrayList<>();
    }

    public void setCalculations(List<Calculation> calculations) {
        this.calculations = calculations;
        notifyDataSetChanged();
    }

    public void addCalculation(Calculation calculation) {
        calculations.add(calculation);
        notifyDataSetChanged();
    }

    class CalculationsViewHolder extends RecyclerView.ViewHolder {
        CalculationCardBinding binding;

        CalculationsViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CalculationCardBinding binding = CalculationCardBinding.inflate(inflater, parent, false);
        return new CalculationsViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((CalculationsViewHolder) holder).binding.setCalculation(calculations.get(position));
        ((CalculationsViewHolder) holder).binding.setHandler((v) -> {
            // click
        });
    }

    @Override
    public int getItemCount() {
        return calculations.size();
    }
}