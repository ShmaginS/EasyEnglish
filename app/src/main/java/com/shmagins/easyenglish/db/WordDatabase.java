package com.shmagins.easyenglish.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = Word.class, version = 2, exportSchema = false)
public abstract class WordDatabase extends RoomDatabase {
    public abstract WordDao wordDao();
}
