package com.shmagins.superbrain.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CalcGameLevel.class, PairGameLevel.class, Stats.class}, version = 12, exportSchema = false)
public abstract class GameDatabase extends RoomDatabase {
    public abstract GameDao gameDao();
}
