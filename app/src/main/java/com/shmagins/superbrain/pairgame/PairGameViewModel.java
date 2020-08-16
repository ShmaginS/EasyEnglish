package com.shmagins.superbrain.pairgame;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.shmagins.superbrain.common.ListGenerator;

import java.util.List;

import io.reactivex.Single;

public class PairGameViewModel extends AndroidViewModel {
    private PairGame game;

    public PairGameViewModel(@NonNull Application application) {
        super(application);
    }

    public Single<PairGame> getGame(int width, int height, int screens, int count) {
        return Single.fromCallable(() -> {
            List<Integer> elements = ListGenerator.generateResourceList(width * height * screens, SmileImages.images, count);
            return getGame(width * height, elements);
        });
    }

    private PairGame getGame(int size, List<Integer> elements) {
        if (game == null || game.isFinished()) {
            synchronized (PairGame.class) {
                if (game == null || game.isFinished()) {
                    game = new PairGame.Builder()
                            .setElements(elements)
                            .setSize(size)
                            .setSecondsPerElement(3)
                            .setSuccessIncrement(1000)
                            .setTimerPeriod(500)
                            .create();
                }
            }
        }
        return game;
    }

}
