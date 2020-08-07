package com.shmagins.superbrain.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.superbrain.MemoryGame;
import com.shmagins.superbrain.databinding.SelectionImageGameCardBinding;

public class MemoryGameSelectionAdapter extends RecyclerView.Adapter {

    private MemoryGame game;

    public MemoryGameSelectionAdapter(MemoryGame game) {
        this.game = game;
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        SelectionImageGameCardBinding binding;

        ImageViewHolder(@NonNull SelectionImageGameCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("WrongConstant")
        public void bind(int position) {
            binding.setRes(game.getSelection().get(position));
            binding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SelectionImageGameCardBinding binding = SelectionImageGameCardBinding.inflate(inflater, parent, false);
        return new ImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ImageViewHolder h = (ImageViewHolder) holder;
        h.bind(position);
    }

    @Override
    public int getItemCount() {
        return game.getSelection().size();
    }
}
