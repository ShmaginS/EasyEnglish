package com.shmagins.superbrain.common;

import com.shmagins.superbrain.calcgame.CalcGameLevel;
import com.shmagins.superbrain.memorygame.MemoryGameLevel;
import com.shmagins.superbrain.pairgame.PairGameLevel;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class GameRepository {
    GameDatabase database;
    @Inject
    public GameRepository(GameDatabase database) {
        this.database = database;
    }

    public Single<Stats> getLevelStats(int game, int lvl) {
        return database.gameDao().getStatsForLevel(game, lvl);
    }

    public Observable<List<Stats>> getGameLevels(int game) {
        return database.gameDao().getStatsForGame(game);
    }

    public Observable<List<PairGameLevel>> getPairGameLevels() {
        return database.gameDao().getPairGameLevels();
    }

    public Observable<List<MemoryGameLevel>> getMemoryGameLevels() {
        return database.gameDao().getMemoryGameLevels();
    }

    public Single<CalcGameLevel> getCalcGameLevel(int lvl) {
        return database.gameDao().getCalcGameLevel(lvl);
    }

    public void setGameStats(int game, int lvl, int time, int errors, int stars) {
        database.gameDao().getStatsForLevel(game, lvl)
                .subscribeOn(Schedulers.newThread())
                .subscribe(stats -> {
                    if (stats.stars <= stars) {
                        database.gameDao().updateStats(game, lvl, stats.time > 0 ? time : 100000, stars, errors);
                    }
                });
    }

    public Single<PairGameLevel> getPairGameLevel(int level) {
        return database.gameDao().getPairGameLevel(level);
    }
}