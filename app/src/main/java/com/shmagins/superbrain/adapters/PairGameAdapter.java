package com.shmagins.superbrain.adapters;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.superbrain.PairGame;
import com.shmagins.superbrain.R;
import com.shmagins.superbrain.databinding.ImageGameCardBinding;

public class PairGameAdapter extends RecyclerView.Adapter {
    private PairGame game;

    public PairGameAdapter(PairGame game) {
        this.game = game;
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageGameCardBinding binding;

        ImageViewHolder(@NonNull ImageGameCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("WrongConstant")
        public void bind(int position) {
            binding.setSelected(game.isPositionSelected(position));
            binding.setRes(game.getElement(position));
            binding.setVisible(game.isPositionVisible(position));
            binding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ImageGameCardBinding binding = ImageGameCardBinding.inflate(inflater, parent, false);
        return new PairGameAdapter.ImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ImageViewHolder h = (ImageViewHolder) holder;
        h.bind(position);
        h.binding.memoryImage.setOnClickListener((view -> {
            game.userPressed(position);
        }));
    }

    @Override
    public int getItemCount() {
        return game.getSize();
    }
}
