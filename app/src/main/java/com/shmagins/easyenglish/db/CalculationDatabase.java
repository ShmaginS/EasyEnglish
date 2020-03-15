package com.shmagins.easyenglish.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = Calculation.class, version = 7, exportSchema = false)
public abstract class CalculationDatabase extends RoomDatabase {
    public abstract CalculationDao calculationDao();
}
