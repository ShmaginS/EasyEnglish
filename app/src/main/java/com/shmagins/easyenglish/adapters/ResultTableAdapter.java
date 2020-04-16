package com.shmagins.easyenglish.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.easyenglish.CalcGame;
import com.shmagins.easyenglish.R;
import com.shmagins.easyenglish.databinding.ResultCardBinding;
import com.shmagins.easyenglish.db.Calculation;

public class ResultTableAdapter extends RecyclerView.Adapter{
    private CalcGame game;

    public ResultTableAdapter(CalcGame game) {
        this.game = game;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
            ResultCardBinding binding;

            ViewHolder(@NonNull ResultCardBinding  binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind(int position){
                Calculation c = game.getCalculation(position);
                binding.resultText.setText(
                        binding
                                .resultText
                                .getContext()
                                .getResources()
                                .getString(
                                        R.string.result_card_text, c.first, c.operation, c.second, c.result, c.answer
                                        ));
                binding.setRight(c.answer == c.result);
                binding.executePendingBindings();
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            ResultCardBinding binding = ResultCardBinding.inflate(inflater, parent, false);
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ViewHolder h = (ViewHolder) holder;
            h.bind(position);
        }

        @Override
        public int getItemCount() {
            return game.getCalculationCount();
        }
}
