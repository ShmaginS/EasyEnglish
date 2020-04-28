package com.shmagins.superbrain.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.shmagins.superbrain.Calculation;

@Database(entities = HistoryRecord.class, version = 9, exportSchema = false)
public abstract class HistoryDatabase extends RoomDatabase {
    public abstract HistoryDao historyDao();
}
