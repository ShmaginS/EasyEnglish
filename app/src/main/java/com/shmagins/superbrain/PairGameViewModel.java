package com.shmagins.superbrain;

import android.app.Application;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

public class PairGameViewModel extends AndroidViewModel {
    private PairGame game;

    public PairGameViewModel(@NonNull Application application) {
        super(application);
    }

    public PairGame getGame(int size, List<Integer> elements) {
        if (game == null || game.isFinished()) {
            synchronized (PairGame.class) {
                if (game == null || game.isFinished()) {
                    boolean timer =
                            PreferenceManager.getDefaultSharedPreferences(getApplication())
                                    .getBoolean("pref_pair_game_use_timer", true);
                    game = new PairGame.Builder()
                            .setElements(elements)
                            .setSize(size)
                            .setStartTime(10000)
                            .setSuccessIncrement(1000)
                            .setTimerEnabled(timer)
                            .setTimerPeriod(500)
                            .create();
                }
            }
        }
        return game;
    }

}
