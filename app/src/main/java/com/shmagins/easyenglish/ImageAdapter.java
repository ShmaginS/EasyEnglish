package com.shmagins.easyenglish;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.easyenglish.databinding.CalculationCardBinding;
import com.shmagins.easyenglish.databinding.MemoryCardBinding;
import com.shmagins.easyenglish.db.Calculation;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter {
    List<ImageState> images;

    public void setImages(List<ImageState> images) {
        this.images = images;
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        MemoryCardBinding binding;

        ImageViewHolder(@NonNull MemoryCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("WrongConstant")
        public void bind(ImageState imageState){
            binding.setImageState(imageState);
            binding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MemoryCardBinding binding = MemoryCardBinding.inflate(inflater, parent, false);
        return new ImageAdapter.ImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ImageViewHolder h = (ImageViewHolder) holder;
        h.bind(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}
