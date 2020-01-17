package com.shmagins.easyenglish.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.easyenglish.R;
import com.shmagins.easyenglish.viewmodel.WordsAdapter;

public class WordsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_words, container, false);
        RecyclerView recycler = fragmentView
                .findViewById(R.id.word_card_recycler);
        recycler.setAdapter(new WordsAdapter());
        return fragmentView;
    }
}
