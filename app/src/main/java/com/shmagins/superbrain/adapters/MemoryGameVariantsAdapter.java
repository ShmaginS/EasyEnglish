package com.shmagins.superbrain.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.superbrain.MemoryGame;
import com.shmagins.superbrain.databinding.ImageGameCardBinding;

public class MemoryGameVariantsAdapter extends RecyclerView.Adapter {
    private MemoryGame game;

    public MemoryGameVariantsAdapter(MemoryGame game){
        this.game = game;
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageGameCardBinding binding;

        ImageViewHolder(@NonNull ImageGameCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("WrongConstant")
        public void bind(int position){
            binding.setSelected(false);
            binding.setRes(game.getVariants().get(position));
            binding.setVisible(true);
            binding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ImageGameCardBinding binding = ImageGameCardBinding.inflate(inflater, parent, false);
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