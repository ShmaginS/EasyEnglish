package com.shmagins.superbrain;

import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class MemoryGame {
    private PublishSubject<Pair<Integer, GameEvent>> events;
    private List<Integer> elements;
    private List<Integer> variants;
    private volatile List<Integer> selection;
    private CompositeDisposable disposable;
    private int milliseconds;
    private long startTime;
    private Disposable timer;
    private long timerPeriod;
    private int errors;
    private boolean isReady;

    public MemoryGame() {
        events = PublishSubject.create();
        elements = new ArrayList<>();
        variants = new ArrayList<>();
        selection = new ArrayList<>();
        disposable = new CompositeDisposable();
        timerPeriod = 1500;
        errors = 0;
        isReady = false;
        milliseconds = 0;
        startTime = 0;
    }

    public void startGame() {
        startTime = SystemClock.elapsedRealtime();
        Log.d("TAG", "startGame: " + startTime);
        if (!isReady) {
            timer = Observable.zip(
                    Observable.interval(timerPeriod, TimeUnit.MILLISECONDS), Observable.fromIterable(elements), (t, e) -> e)
                    .subscribe(e -> events.onNext(new Pair<>(e, GameEvent.TIMER)),
                            ex -> events.onError(ex),
                            () -> {
                                events.onNext(new Pair<>(elements.size(), GameEvent.UPDATE));
                                isReady = true;
                            });
        }
    }

    public void pauseGame() {
        milliseconds += SystemClock.elapsedRealtime() - startTime;
        Log.d("TAG", "milliseconds: " + milliseconds);
        if (isReady) {
            timer.dispose();
        }
    }

    public int stopGame() {
        disposable.dispose();
        milliseconds += SystemClock.elapsedRealtime() - startTime;
        return milliseconds;
    }

    public void subscribe(Consumer<Pair<Integer, GameEvent>> consumer) {
        disposable.add(events
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer));
    }

    public void userPressed(int position) {
        int variant = variants.get(position);
        int currentPos = selection.size();
        int right = elements.get(currentPos);
        int size = elements.size();

        if (variant == right) {
            events.onNext(new Pair<>(0, GameEvent.SELECT));
            events.onNext(new Pair<>(0, GameEvent.SOUND));
            selection.add(variant);
        } else {
            events.onNext(new Pair<>(size - errors, GameEvent.SOUND));
            events.onNext(new Pair<>(1, GameEvent.FAIL));
            errors++;
        }
        if (errors == size) {
            events.onNext(new Pair<>(errors, GameEvent.LOSE));
        }
        if (selection.size() == size) {
            events.onNext(new Pair<>(errors, GameEvent.WIN));
        }

    }

    public List<Integer> getElements() {
        return elements;
    }

    public List<Integer> getVariants() {
        return variants;
    }

    public List<Integer> getSelection() {
        return selection;
    }

    public void setElements(List<Integer> elements) {
        this.elements = elements;
    }

    public void setVariants(List<Integer> variants) {
        this.variants = variants;
    }

    public boolean isFinished() {
        return selection.size() == elements.size();
    }

    public int getErrors() {
        return errors;
    }

    public boolean isReady() {
        return isReady;
    }

    public int getTime(){
        return milliseconds;
    }

    public static class Rules {

        public static int getStarsForResult(int total, int errors) {
            int stars = 3;
            if ((total - errors) * 100 / total < 90)
                stars--;
            if ((total - errors) * 100 / total < 50)
                stars--;
            if ((total - errors) * 100 / total == 0)
                stars--;
            return stars;
        }
    }
}
