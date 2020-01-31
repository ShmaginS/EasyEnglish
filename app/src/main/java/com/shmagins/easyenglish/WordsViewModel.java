package com.shmagins.easyenglish;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.shmagins.easyenglish.WordsApplication;
import com.shmagins.easyenglish.db.Word;
import com.shmagins.easyenglish.db.WordDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class WordsViewModel extends AndroidViewModel {
    @Inject
    WordDatabase wdb;
    private WordsRepository repository;

    public WordsViewModel(@NonNull Application application) {
        super(application);
        ((WordsApplication)application).getApplicationComponent()
                .inject(this);
        repository = new WordsRepository(wdb);

        List<Word> testWords = new ArrayList<>();
        testWords.add(new Word("Word 1", "Слово 1", "En-Ru", "[ЫЫЫ]",0));
        testWords.add(new Word("Word 2", "Слово 2", "En-Ru", "[ЫЫЫ]", 0));
        testWords.add(new Word("Word 3", "Слово 3", "En-Ru", "[ЫЫЫ]", 0));
        testWords.add(new Word("Word 4", "Слово 4", "En-Ru", "[ЫЫЫ]", 0));
        testWords.add(new Word("Word 5", "Слово 5", "En-Ru", "[ЫЫЫ]", 0));
        testWords.add(new Word("Word 6", "Слово 6", "En-Ru", "[ЫЫЫ]", 0));
        testWords.add(new Word("Word 7", "Слово 7", "En-Ru", "[ЫЫЫ]", 0));
        repository.insertWords(testWords);

    }

    public Observable<List<Word>> getAll(){
        return wdb.wordDao().getAll();
    }
}