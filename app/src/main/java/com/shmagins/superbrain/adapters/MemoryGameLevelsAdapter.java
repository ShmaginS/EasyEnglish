package com.shmagins.superbrain.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.superbrain.databinding.LevelCardBinding;
import com.shmagins.superbrain.db.Stats;
import com.shmagins.superbrain.view.MemoryGameActivity;

import java.util.List;

public class MemoryGameLevelsAdapter extends RecyclerView.Adapter {

    private List<Stats> levels;

    public void setLevels(List<Stats> levels) {
        this.levels = levels;
    }

    public static class MemoryGameLevelViewHolder extends RecyclerView.ViewHolder {
        public LevelCardBinding binding;

        MemoryGameLevelViewHolder(@NonNull LevelCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Stats lvlStats) {
            binding.setLvl(lvlStats);
            binding.textView.setOnClickListener(view -> {
                int lvl = lvlStats.lvl;
                Intent intent = MemoryGameActivity.getStartIntent(this.itemView.getContext());
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
        h.bind(levels.get(position));
    }

    @Override
    public int getItemCount() {
        return levels == null ? 0 : levels.size();
    }
}