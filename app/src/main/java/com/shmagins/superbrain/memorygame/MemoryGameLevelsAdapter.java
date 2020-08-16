package com.shmagins.superbrain.memorygame;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.superbrain.databinding.LevelCardBinding;
import com.shmagins.superbrain.common.Stats;

import java.util.List;

public class MemoryGameLevelsAdapter extends RecyclerView.Adapter {

    public void setLevelStats(List<Stats> levelStats) {
        this.levelStats = levelStats;
    }

    public void setLevels(List<MemoryGameLevel> levels) {
        this.levels = levels;
    }

    public static class MemoryGameLevelViewHolder extends RecyclerView.ViewHolder {
        public LevelCardBinding binding;

        MemoryGameLevelViewHolder(@NonNull LevelCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Stats lvlStats, MemoryGameLevel level) {
            binding.setLvl(lvlStats);
            binding.textView.setOnClickListener(view -> {
                int lvl = lvlStats.lvl;
                Intent intent = MemoryGameActivity.getStartIntent(this.itemView.getContext(), lvl, level.usedImages, level.gameFieldWidth, level.gameFieldHeight);
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
        return new MemoryGameLevelViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MemoryGameLevelViewHolder h = (MemoryGameLevelViewHolder) holder;
        h.bind(levelStats.get(position), levels.get(position));
    }

    @Override
    public int getItemCount() {
        return levelStats == null ? 0 : levelStats.size();
    }

    private List<Stats> levelStats;
    private List<MemoryGameLevel> levels;
}