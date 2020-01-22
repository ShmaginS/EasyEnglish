package com.shmagins.easyenglish.model;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {
    @Provides
    @Singleton
    WordDao provideWordDao(WordDatabase db) {
        return db.wordDao();
    }
}
