package com.shmagins.superbrain.memorygame;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.shmagins.superbrain.common.ListGenerator;
import com.shmagins.superbrain.common.Util;

import java.util.List;

import io.reactivex.Single;

public class MemoryGameViewModel extends AndroidViewModel {
    public MemoryGameViewModel(@NonNull Application application) {
        super(application);
    }

    public Single<MemoryGame> getGame(int usedImages, int gameFieldWidth, int gameFieldHeight) {
        return Single.fromCallable(() -> {
            List<Integer> elements = ListGenerator.generateImageList(
                    ListGenerator.generateImageIndexList(usedImages, AnimalImages.images.length),
                    AnimalImages.images);
            List<Integer> animals = Util.toArrayList(AnimalImages.images, Integer.class);
            List<Integer> variants = ListGenerator.appendAndShuffle(elements, animals, gameFieldWidth * gameFieldHeight);
            return getGame(elements, variants);
        });
    }

    private MemoryGame getGame(List<Integer> elements, List<Integer> variants) {
        if (game == null || game.isFinished()) {
            synchronized (MemoryGame.class) {
                if (game == null || game.isFinished()) {
                    game = new MemoryGame();
                    game.setElements(elements);
                    game.setVariants(variants);
                }
            }
        }
        return game;
    }

    private MemoryGame game;
}
