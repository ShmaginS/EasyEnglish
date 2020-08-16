package com.shmagins.superbrain.common;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.shmagins.superbrain.calcgame.CalcGameLevel;
import com.shmagins.superbrain.memorygame.MemoryGameLevel;
import com.shmagins.superbrain.pairgame.PairGameLevel;

@Database(entities = {CalcGameLevel.class, PairGameLevel.class, MemoryGameLevel.class, Stats.class}, version = 15, exportSchema = false)
public abstract class GameDatabase extends RoomDatabase {
    public abstract GameDao gameDao();
}
