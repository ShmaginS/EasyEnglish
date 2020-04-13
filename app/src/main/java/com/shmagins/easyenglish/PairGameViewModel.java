package com.shmagins.easyenglish;

import androidx.lifecycle.ViewModel;

public class PairGameViewModel extends ViewModel {
    PairGame game;

    public PairGame getGame() {
        if (game == null) {
            synchronized (PairGame.class) {
                if (game == null) {
                    game = new PairGame();
                }
            }
        } else if (game.isFinished()) {
            synchronized (PairGame.class) {
                if (game.isFinished()) {
                    game = new PairGame();
                }
            }
        }
        return game;
    }

}
