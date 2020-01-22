package com.shmagins.easyenglish.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.shmagins.easyenglish.model.Word;
import com.shmagins.easyenglish.model.WordDatabase;
import com.shmagins.easyenglish.model.WordsApplication;

import javax.inject.Inject;

public class WordsViewModel extends AndroidViewModel {
    @Inject
    WordDatabase wdb;

    public WordsViewModel(@NonNull Application application) {
        super(application);
        ((WordsApplication)application).getApplicationComponent()
                .inject(this);
        wdb.wordDao().insert(new Word("Word 1", "Слово 1", "EnRu",  0));
        wdb.wordDao().insert(new Word("Word 2", "Слово 2", "EnRu",  0));
        wdb.wordDao().insert(new Word("Word 3", "Слово 3", "EnRu",  0));
        wdb.wordDao().insert(new Word("Word 4", "Слово 4", "EnRu",  0));
        wdb.wordDao().insert(new Word("Word 5", "Слово 5", "EnRu",  0));
        wdb.wordDao().insert(new Word("Word 6", "Слово 6", "EnRu",  0));
        wdb.wordDao().insert(new Word("Word 7", "Слово 7", "EnRu",  0));
    }
}
