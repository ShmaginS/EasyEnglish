package com.shmagins.easyenglish;

import android.content.Context;

import androidx.room.Room;

import com.shmagins.easyenglish.db.CalculationDao;
import com.shmagins.easyenglish.db.CalculationDatabase;

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
    CalculationDatabase provideWordDatabase() {
        return Room.databaseBuilder(
                appContext,
                CalculationDatabase.class,
                databaseName
        ).fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    CalculationDao provideWordDao(CalculationDatabase db) {
        return db.calculationDao();
    }

    private Context appContext;
    private final String databaseName = "word_database.db";
}
