package com.shmagins.easyenglish.model;

import android.content.Context;

import androidx.room.Room;

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
    WordDatabase provideWordDatabase() {
        return Room.databaseBuilder(
                appContext,
                WordDatabase.class,
                databaseName
        ).fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    WordDao provideWordDao(WordDatabase db) {
        return db.wordDao();
    }

    private Context appContext;
    private final String databaseName = "word_database.db";
}
