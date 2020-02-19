package com.shmagins.easyenglish;

import android.os.AsyncTask;
import android.util.Log;

import com.shmagins.easyenglish.db.Word;
import com.shmagins.easyenglish.db.WordDatabase;

import java.util.List;
import java.util.concurrent.Callable;


import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WordsRepository {
    WordDatabase wdb;
    @Inject
    public WordsRepository(WordDatabase wdb) {
        this.wdb = wdb;
    }

    public Observable<List<Word>> getAllWords(){
        return wdb.wordDao().getAll();
    }
    public void insertWords(List<Word> words) {
        Observable.fromIterable(words)
                .subscribeOn(Schedulers.newThread())
                .subscribe(word -> wdb.wordDao().insert(word),
                        throwable -> Log.d("happy", "insertWords: insertion error"));
    }

    public void updateWords(List<Word> words){
        Observable.fromIterable(words)
                .subscribeOn(Schedulers.newThread())
                .subscribe(word -> wdb.wordDao().insert(word),
                        throwable -> Log.d("happy", "insertWords: update error"));
    }

    public void deleteAll() {
        new DeleteAllTask(wdb);

    }
}

class DeleteAllTask extends AsyncTask<Void, Void, Void> {
    WordDatabase wdb;

    public DeleteAllTask(WordDatabase wdb) {
        this.wdb = wdb;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        wdb.wordDao().deleteAll();
        return null;
    }
}