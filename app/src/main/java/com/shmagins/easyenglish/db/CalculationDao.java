package com.shmagins.easyenglish.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface CalculationDao {
    @Query("SELECT * FROM Calculation")
    Observable<List<Calculation>> getAll();

    @Query("SELECT * FROM Calculation LIMIT :limit")
    Observable<List<Calculation>> getAllWithLimit(int limit);

    @Query("SELECT * FROM Calculation WHERE id=:id")
    Calculation getCalculation(int id);

    @Query("DELETE FROM Calculation where 1 = 1")
    void deleteAll();


    @Insert
    void insert(Calculation calculation);

    @Update
    void update(Calculation calculation);

    @Delete
    void delete(Calculation calculation);
}
