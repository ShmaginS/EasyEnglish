package com.shmagins.superbrain.memorygame;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.superbrain.memorygame.MemoryGame;
import com.shmagins.superbrain.databinding.VariantsImageGameCardBinding;

public class MemoryGameVariantsAdapter extends RecyclerView.Adapter {
    private MemoryGame game;

    public MemoryGameVariantsAdapter(MemoryGame game) {
        this.game = game;
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        VariantsImageGameCardBinding binding;

        ImageViewHolder(@NonNull VariantsImageGameCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("WrongConstant")
        public void bind(int position) {
            binding.setRes(game.getVariants().get(position));
            binding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        VariantsImageGameCardBinding binding = VariantsImageGameCardBinding.inflate(inflater, parent, false);
        return new ImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ImageViewHolder h = (ImageViewHolder) holder;
        h.bind(position);
        h.binding.memoryImage.setOnClickListener((view -> game.userPressed(position)));
    }

    @Override
    public int getItemCount() {
        return game.getVariants().size();
    }
}