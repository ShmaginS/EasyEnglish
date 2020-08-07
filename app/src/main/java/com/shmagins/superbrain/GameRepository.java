package com.shmagins.superbrain;

import com.shmagins.superbrain.db.CalcGameLevel;
import com.shmagins.superbrain.db.GameDatabase;
import com.shmagins.superbrain.db.PairGameLevel;
import com.shmagins.superbrain.db.Stats;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
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

    public Single<CalcGameLevel> getCalcGameLevel(int lvl) {
        return database.gameDao().getCalcGameLevel(lvl);
    }

    public void setGameStats(int game, int lvl, int time, int errors, int stars) {
        database.gameDao().getStatsForLevel(game, lvl)
                .subscribeOn(Schedulers.newThread())
                .subscribe(stats -> {
                    if (stats.stars <= stars) {
                        database.gameDao().updateStats(game, lvl, stats.time == 0 ? time : Math.min(time, stats.time), stars, errors);
                    }
                });
    }

    public Single<PairGameLevel> getPairGameLevel(int level) {
        return database.gameDao().getPairGameLevel(level);
    }
}