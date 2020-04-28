package com.shmagins.superbrain.dagger;

import android.content.Context;

import androidx.room.Room;

import com.shmagins.superbrain.db.HistoryDao;
import com.shmagins.superbrain.db.HistoryDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    public DatabaseModule(Context appContext) {
        this.appContext = appContext;
    }

    @Provides
    @Singleton
    HistoryDatabase provideWordDatabase() {
        return Room.databaseBuilder(
                appContext,
                HistoryDatabase.class,
                databaseName
        ).fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    HistoryDao provideWordDao(HistoryDatabase db) {
        return db.historyDao();
    }

    private Context appContext;
    private final String databaseName = "word_database.db";
}
