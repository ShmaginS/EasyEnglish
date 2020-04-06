package com.shmagins.easyenglish;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.easyenglish.databinding.PairGameCardBinding;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter {
    private List<ImageState> images;

    public void setImages(List<ImageState> images) {
        this.images = images;
        PairGame.getInstance().subscribe(integerEventPair -> {
            switch (integerEventPair.second) {
                case SELECT:
                    this.images.get(integerEventPair.first).isSelected = true;
                    break;
                case DESELECT:
                    this.images.get(integerEventPair.first).isSelected = false;
                    break;
                case SUCCESS:
                    this.images.get(integerEventPair.first).isOpened = false;
            }
        });
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        PairGameCardBinding binding;

        ImageViewHolder(@NonNull PairGameCardBinding binding) {
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
        PairGameCardBinding binding = PairGameCardBinding.inflate(inflater, parent, false);
        return new ImageAdapter.ImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ImageViewHolder h = (ImageViewHolder) holder;
        h.bind(images.get(position));
        h.binding.memoryImage.setOnClickListener((view -> PairGame.getInstance().userPressed(position)));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}
