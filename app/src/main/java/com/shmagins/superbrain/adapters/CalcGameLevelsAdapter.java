package com.shmagins.superbrain.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.superbrain.databinding.LevelCardBinding;
import com.shmagins.superbrain.db.PairGameLevel;
import com.shmagins.superbrain.db.Stats;
import com.shmagins.superbrain.view.CalcGameActivity;

import java.util.List;

public class CalcGameLevelsAdapter extends RecyclerView.Adapter {

    private List<Stats> levelStats;

    public void setLevelStats(List<Stats> levels) {
        this.levelStats = levels;
    }

    public static class CalcGameLevelViewHolder extends RecyclerView.ViewHolder {
        public LevelCardBinding binding;

        CalcGameLevelViewHolder(@NonNull LevelCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Stats lvlStats) {
            binding.setLvl(lvlStats);
            binding.textView.setOnClickListener(view -> {
                int lvl = lvlStats.lvl;
                Intent intent = CalcGameActivity.getStartIntent(this.itemView.getContext(), lvl);
                this.itemView.getContext().startActivity(intent);
            });
            binding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        LevelCardBinding binding = LevelCardBinding.inflate(inflater, parent, false);
        return new CalcGameLevelViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CalcGameLevelViewHolder h = (CalcGameLevelViewHolder) holder;
        h.bind(levelStats.get(position));
    }

    @Override
    public int getItemCount() {
        return levelStats == null ? 0 : levelStats.size();
    }
}