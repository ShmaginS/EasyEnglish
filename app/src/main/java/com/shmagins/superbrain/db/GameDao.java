package com.shmagins.superbrain.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.shmagins.superbrain.PairGame;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface GameDao {
    @Insert
    void insertCalcGameLevels(List<CalcGameLevel> levels);

    @Insert
    void insertStats(List<Stats> stats);

    @Query("SELECT * FROM CalcGameLevel WHERE lvl = :lvl")
    Single<CalcGameLevel> getCalcGameLevel(int lvl);

    @Query("SELECT * FROM PairGameLevel WHERE lvl = :lvl")
    Single<PairGameLevel> getPairGameLevel(int lvl);

    @Query("SELECT * FROM Stats WHERE game = :game AND lvl = :lvl")
    Single<Stats> getStatsForLevel(int game, int lvl);

    @Update
    void updateCalcGameLevel(CalcGameLevel level);

    @Update
    void updateStats(Stats stats);

    @Query("SELECT * FROM Stats WHERE game = :game ORDER BY lvl")
    Observable<List<Stats>> getStatsForGame(int game);

    @Query("UPDATE Stats SET time = :time, stars = :stars, errors = :errors WHERE game = :game AND lvl = :lvl")
    void updateStats(int game, int lvl, int time, int stars, int errors);

    @Query("SELECT * FROM PairGameLevel ORDER BY lvl")
    Observable<List<PairGameLevel>> getPairGameLevels();

    @Query("SELECT * FROM MemoryGameLevel ORDER BY lvl")
    Observable<List<MemoryGameLevel>> getMemoryGameLevels();
}
