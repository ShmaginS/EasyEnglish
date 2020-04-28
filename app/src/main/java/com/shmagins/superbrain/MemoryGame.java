package com.shmagins.superbrain;

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
    private Disposable timer;
    private long timerPeriod;

    public MemoryGame() {
        events = PublishSubject.create();
        elements = new ArrayList<>();
        variants = new ArrayList<>();
        selection = new ArrayList<>();
        disposable = new CompositeDisposable();
        timerPeriod = 1500;
    }

    public void startGame() {
        timer = Observable.zip(
                Observable.interval(timerPeriod, TimeUnit.MILLISECONDS), Observable.fromIterable(elements), (t, e) -> e)
                .subscribe(e -> events.onNext(new Pair<>(e, GameEvent.TIMER)),
                        ex -> events.onError(ex),
                        () -> events.onNext(new Pair<>(elements.size(), GameEvent.UPDATE)));
    }

    public void pauseGame() {
        timer.dispose();
    }

    public void stopGame() {
        disposable.dispose();
    }

    public void subscribe(Consumer<Pair<Integer, GameEvent>> consumer) {
        disposable.add(events
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer));
    }

    public void userPressed(int position) {
        selection.add(variants.get(position));
        events.onNext(new Pair<>(position, GameEvent.SELECT));
        int size = elements.size();
        Log.d("happy", "userPressed: " + selection + " " + elements);
        if (selection.size() == size) {
            Log.d("happy", "userPressed: " + selection.size() + " " + elements.size());
            if (isSelectionCorrect()) {
                Log.d("happy", "userPressed: WIN");
                events.onNext(new Pair<>(size, GameEvent.WIN));
            } else {
                Log.d("happy", "userPressed: LOSE");
                events.onNext(new Pair<>(size, GameEvent.LOSE));
            }
        }
    }

    private boolean isSelectionCorrect() {
        Iterator<Integer> it1 = selection.iterator();
        Iterator<Integer> it2 = elements.iterator();
        while (it1.hasNext()) {
            if (!it1.next().equals(it2.next())){
                return false;
            }
        }
        return true;
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
}
