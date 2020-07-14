package com.shmagins.superbrain.dagger;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.shmagins.superbrain.BrainApplication;
import com.shmagins.superbrain.HistoryRepository;
import com.shmagins.superbrain.db.HistoryDao;
import com.shmagins.superbrain.db.HistoryDatabase;

import javax.inject.Singleton;

import dagger.Component;
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
    HistoryDao provideHistoryDao(HistoryDatabase db) {
        return db.historyDao();
    }

    @Provides
    HistoryRepository provideHistoryRepository() {return new HistoryRepository(provideHistoryDatabase());}

    @Component(modules = {
            DatabaseModule.class
    })
    public interface DatabaseComponent {
        HistoryRepository getHistoryRepository();
    }

    private Context appContext;
    private final String databaseName = "word_database.db";
}
