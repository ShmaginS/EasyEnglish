package com.shmagins.easyenglish;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.easyenglish.databinding.WordCardBinding;
import com.shmagins.easyenglish.db.Calculation;

import java.util.ArrayList;
import java.util.List;

public class WordsAdapter extends RecyclerView.Adapter {
    private List<Calculation> calculations;

    {
        calculations = new ArrayList<>();
    }

    public void setCalculations(List<Calculation> calculations) {
        this.calculations = calculations;
        notifyDataSetChanged();
    }

    public void addWord(Calculation calculation) {
        calculations.add(calculation);
        notifyDataSetChanged();
    }

    class WordsViewHolder extends RecyclerView.ViewHolder {
        WordCardBinding binding;

        WordsViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        WordCardBinding binding = WordCardBinding.inflate(inflater, parent, false);
        return new WordsViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((WordsViewHolder) holder).binding.setCalculation(calculations.get(position));
        ((WordsViewHolder) holder).binding.setHandler((v) -> {
            ((WordsViewHolder) holder).binding.calculation.setText("AZAZA " + v);
        });
    }

    @Override
    public int getItemCount() {
        return calculations.size();
    }
}