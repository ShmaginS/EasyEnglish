package com.shmagins.superbrain;

import android.os.AsyncTask;
import android.util.Log;

import com.shmagins.superbrain.db.HistoryDatabase;
import com.shmagins.superbrain.db.HistoryRecord;

import java.util.List;


import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class HistoryRepository {
    HistoryDatabase hdb;
    @Inject
    public HistoryRepository(HistoryDatabase hdb) {
        this.hdb = hdb;
    }

    public Observable<List<HistoryRecord>> getAllRecords(){
        return hdb.historyDao().getAll();
    }

    public void insertHistoryRecord(List<HistoryRecord> records) {
        Observable.fromIterable(records)
                .subscribeOn(Schedulers.newThread())
                .subscribe(rec -> hdb.historyDao().insert(rec),
                        throwable -> Log.d("happy", "insert error"));
    }

    public void updateHistoryRecord(HistoryRecord record){
        Observable.just(record)
                .subscribeOn(Schedulers.newThread())
                .subscribe(calc -> hdb.historyDao().update(calc),
                        throwable -> Log.d("happy", "insert error"));
    }



    public void deleteAll() {
        DeleteAllTask task = new DeleteAllTask(hdb);
        task.execute();

    }
}

class DeleteAllTask extends AsyncTask<Void, Void, Void> {
    HistoryDatabase wdb;

    public DeleteAllTask(HistoryDatabase wdb) {
        this.wdb = wdb;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        wdb.historyDao().deleteAll();
        return null;
    }
}