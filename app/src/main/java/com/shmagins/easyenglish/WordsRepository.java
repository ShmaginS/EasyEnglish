package com.shmagins.easyenglish;

import com.shmagins.easyenglish.db.Word;
import com.shmagins.easyenglish.db.WordDatabase;

import java.util.List;


import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
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
                .subscribeOn(Schedulers.io())
                .subscribe(word -> wdb.wordDao().insert(word));
    }
}
