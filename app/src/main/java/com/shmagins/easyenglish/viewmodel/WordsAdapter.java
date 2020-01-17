package com.shmagins.easyenglish.viewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.easyenglish.databinding.WordCardBinding;
import com.shmagins.easyenglish.model.Word;

import java.util.ArrayList;
import java.util.List;

public class WordsAdapter extends RecyclerView.Adapter {
    private List<Word> words;
    {
        words = new ArrayList<>();
        words.add(new Word("Pidor", "Пидор", "En", true, 0));
        words.add(new Word("Jopa", "Жопа", "En", true, 0));
    }

    class WordsViewHolder extends RecyclerView.ViewHolder{
        WordCardBinding binding;

        public WordsViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        WordCardBinding binding = WordCardBinding.inflate(inflater, parent, false);
        return new WordsViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((WordsViewHolder)holder).binding.setWord(words.get(position));
    }

    @Override
    public int getItemCount() {
        return words.size();
    }
}
