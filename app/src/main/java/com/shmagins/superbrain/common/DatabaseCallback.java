package com.shmagins.superbrain.common;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.shmagins.superbrain.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DatabaseCallback extends RoomDatabase.Callback {
    private Context appContext;

    public DatabaseCallback(Context appContext) {
        super();
        this.appContext = appContext;
    }

    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
        prepopulateFromRaw(db, R.raw.initial);
    }

    @Override
    public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
        prepopulateFromRaw(db, R.raw.initial);
    }

    private void prepopulateFromRaw(SupportSQLiteDatabase db, int resource) {
        BufferedReader br = new BufferedReader(new InputStreamReader(appContext.getResources().openRawResource(resource)));
        db.beginTransaction();
        try {
            while (br.ready()) {
                db.execSQL(br.readLine());
            }
            db.setTransactionSuccessful();
            br.close();
        } catch (Exception e) {
            Log.d("DatabaseCallback", e.toString());
        } finally {
            db.endTransaction();
        }
    }
}
