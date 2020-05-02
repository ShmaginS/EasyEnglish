package com.shmagins.superbrain.dagger;

import android.content.Context;

import androidx.room.Room;

import com.shmagins.superbrain.HistoryRepository;
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
    HistoryDatabase provideHistoryDatabase() {
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

    @Provides
    HistoryRepository provideHistoryRepository() {return new HistoryRepository(provideHistoryDatabase());}

    private Context appContext;
    private final String databaseName = "word_database.db";
}
