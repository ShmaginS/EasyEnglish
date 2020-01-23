package com.shmagins.easyenglish.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface WordDao {
    @Query("SELECT * FROM word")
    Observable<Word> getAll();

    @Query("SELECT * FROM word WHERE word=:word")
    Word getWord(String word);

    @Insert
    void insert(Word word);

    @Update
    void update(Word word);

    @Delete
    void delete(Word word);
}
