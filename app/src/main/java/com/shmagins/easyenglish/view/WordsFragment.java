package com.shmagins.easyenglish.view;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.functions.Consumer;

import com.shmagins.easyenglish.R;
import com.shmagins.easyenglish.model.DaggerApplicationComponent;
import com.shmagins.easyenglish.model.Word;
import com.shmagins.easyenglish.model.WordDatabase;
import com.shmagins.easyenglish.model.WordsApplication;
import com.shmagins.easyenglish.viewmodel.WordsAdapter;
import com.shmagins.easyenglish.viewmodel.WordsViewModel;

import javax.inject.Inject;

public class WordsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_words, container, false);

        RecyclerView recycler = fragmentView
                .findViewById(R.id.word_card_recycler);
        WordsAdapter adapter = new WordsAdapter();
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recycler);
        WordsViewModel viewModel = new ViewModelProvider(getActivity(), new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T)new WordsViewModel(getActivity().getApplication());
            }
        }).get(WordsViewModel.class);
        viewModel.getAll()
                .subscribe(adapter::setWords);
        return fragmentView;
    }
}
