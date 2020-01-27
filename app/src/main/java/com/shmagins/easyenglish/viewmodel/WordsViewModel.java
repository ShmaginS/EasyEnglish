package com.shmagins.easyenglish.viewmodel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.shmagins.easyenglish.model.Word;
import com.shmagins.easyenglish.model.WordDatabase;
import com.shmagins.easyenglish.model.WordsApplication;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class WordsViewModel extends AndroidViewModel {
    @Inject
    WordDatabase wdb;

    public WordsViewModel(@NonNull Application application) {
        super(application);
        ((WordsApplication)application).getApplicationComponent()
                .inject(this);

        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                wdb.wordDao().deleteAll();
                wdb.wordDao().insert(new Word("Word 1", "Слово 1", "En-Ru", 0));
                wdb.wordDao().insert(new Word("Word 2", "Слово 2", "En-Ru", 0));
                wdb.wordDao().insert(new Word("Word 3", "Слово 3", "En-Ru", 0));
                wdb.wordDao().insert(new Word("Word 4", "Слово 4", "En-Ru", 0));
                wdb.wordDao().insert(new Word("Word 5", "Слово 5", "En-Ru", 0));
                wdb.wordDao().insert(new Word("Word 6", "Слово 6", "En-Ru", 0));
                wdb.wordDao().insert(new Word("Word 7", "Слово 7", "En-Ru", 0));
                return null;
            }
        }.execute();
    }

    public Observable<List<Word>> getAll(){
        return wdb.wordDao().getAll();
    }
}
