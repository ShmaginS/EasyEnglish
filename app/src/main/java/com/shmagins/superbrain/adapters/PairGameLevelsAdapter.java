package com.shmagins.superbrain.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.superbrain.databinding.LevelCardBinding;
import com.shmagins.superbrain.db.PairGameLevel;
import com.shmagins.superbrain.db.Stats;
import com.shmagins.superbrain.view.PairGameActivity;

import java.util.List;

public class PairGameLevelsAdapter extends RecyclerView.Adapter {
    private List<Stats> levelStats;
    private List<PairGameLevel> levels;


    public void setLevels(List<PairGameLevel> levels) {
        this.levels = levels;
    }

    public void setLevelStats(List<Stats> levelStats) {
        this.levelStats = levelStats;
    }

    public static class PairGameLevelViewHolder extends RecyclerView.ViewHolder {
        public LevelCardBinding binding;

        PairGameLevelViewHolder(@NonNull LevelCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Stats lvlStats, PairGameLevel level) {
            binding.setLvl(lvlStats);
            binding.textView.setOnClickListener(view -> {
                Intent intent = PairGameActivity.getStartIntent(this.itemView.getContext(), lvlStats.lvl, level.width, level.height, level.screens, level.inc, level.num);
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
        return new PairGameLevelsAdapter.PairGameLevelViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PairGameLevelsAdapter.PairGameLevelViewHolder h = (PairGameLevelViewHolder) holder;
        h.bind(levelStats.get(position), levels.get(position));
    }

    @Override
    public int getItemCount() {
        return levels == null ? 0 : levels.size();
    }
}