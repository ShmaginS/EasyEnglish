package com.shmagins.superbrain.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface HistoryDao {
    @Query("SELECT * FROM HistoryRecord")
    Observable<List<HistoryRecord>> getAll();

    @Query("SELECT * FROM HistoryRecord WHERE id=:id")
    HistoryRecord getCalculation(int id);

    @Query("DELETE FROM HistoryRecord where 1 = 1")
    void deleteAll();


    @Insert
    void insert(HistoryRecord record);

    @Update
    void update(HistoryRecord record);

    @Delete
    void delete(HistoryRecord record);
}
