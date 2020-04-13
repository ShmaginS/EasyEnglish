package com.shmagins.easyenglish;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class PairGame {
    private int startTime;
    private int successIncrement;
    private int timerPeriod;

    private static volatile PairGame instance;
    private PublishSubject<Pair<Integer, Event>> gameEvent;
    private CompositeDisposable disposable;
    private boolean isStarted = false;
    private List<Integer> selection;
    private List<Integer> elements;
    private List<Integer> additional;
    private List<Integer> solved;
    private Disposable timer;
    private int time;

    public int getTime() {
        return time;
    }

    public enum Event {
        SELECT,
        DESELECT,
        SUCCESS,
        TIMER,
        FAIL,
        WIN,
        LOSE
    }

    public PairGame() {

    }

    private PairGame(List<Integer> elements, List<Integer> additionalElements, boolean timerEnabled, int startTime, int successIncrement, int timerPeriod){
        this.elements = elements;
        this.additional = additionalElements;
        gameEvent = PublishSubject.create();
        isStarted = true;
        disposable = new CompositeDisposable();
        selection = new ArrayList<>();
        solved = new ArrayList<>();
        time = startTime;
        this.startTime = startTime;
        this.successIncrement = successIncrement;
        this.timerPeriod = timerPeriod;
    }

    static class Builder{
        List<Integer> elements;
        List<Integer> additionalElements;
        boolean timerEnabled;
        int startTime;
        int successIncrement;
        int timerPeriod;

        Builder(){
            additionalElements = new ArrayList<>();
            timerEnabled = true;
            startTime = 10000;
            successIncrement = 1000;
            timerPeriod = 500;
        }

        public PairGame create() {
            return new PairGame(elements, additionalElements, timerEnabled, startTime, successIncrement, timerPeriod);
        }

        public Builder setElements(List<Integer> elements){
            this.elements = elements;
            return this;
        }

        public Builder setAdditionalElements(List<Integer> additionalElements) {
            this.additionalElements = additionalElements;
            return this;
        }

        public Builder setTimerEnabled(boolean timerEnabled) {
            this.timerEnabled = timerEnabled;
            return this;
        }

        public Builder setStartTime(int startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder setSuccessIncrement(int successIncrement) {
            this.successIncrement = successIncrement;
            return this;
        }

        public Builder setTimerPeriod(int timerPeriod) {
            this.timerPeriod = timerPeriod;
            return this;
        }
    }

    public void createGame(List<Integer> elements) {
        this.elements = elements;
        gameEvent = PublishSubject.create();
        isStarted = true;
        disposable = new CompositeDisposable();
        selection = new ArrayList<>();
        solved = new ArrayList<>();
        time = startTime;
        startTime = 10000;
        successIncrement = 1000;
        timerPeriod = 500;
    }

    public void startGame() {
        timer = Observable.interval(timerPeriod, TimeUnit.MILLISECONDS)
                .subscribe(obs -> {
                    time -= timerPeriod;
                    if (time >= 0) {
                        gameEvent.onNext(new Pair<>(time, Event.TIMER));
                    } else {
                        gameEvent.onNext(new Pair<>(0, Event.LOSE));
                        stopGame();
                    }
                });
    }

    public void pauseGame() {
        timer.dispose();
    }

    public void subscribe(Consumer<Pair<Integer, Event>> consumer) {
        if (!isStarted) {
            throw new IllegalStateException("Must start first");
        }
        disposable.add(gameEvent.subscribe(consumer));
    }

    public void userPressed(int position) {
        if (selection.isEmpty()) {
            selection.add(position);
            gameEvent.onNext(new Pair<>(position, Event.SELECT));
        } else {
            if (elements.get(selection.get(0)).equals(elements.get(position))) {
                gameEvent.onNext(new Pair<>(position, Event.SUCCESS));
                gameEvent.onNext(new Pair<>(selection.get(0), Event.SUCCESS));
                solved.add(selection.get(0));
                solved.add(position);
                time += successIncrement;
                if (isFinished()) {
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            gameEvent.onNext(new Pair<>(position, Event.WIN));
                            stopGame();
                        }
                    }, 1000);
                }
            } else {
                gameEvent.onNext(new Pair<>(position, Event.FAIL));
            }
            gameEvent.onNext(new Pair<>(selection.get(0), Event.DESELECT));
            gameEvent.onNext(new Pair<>(position, Event.DESELECT));
            selection.clear();
        }
    }

    public void stopGame() {
        isStarted = false;
        disposable.dispose();
        if (!timer.isDisposed()) {
            timer.dispose();
        }
    }

    public static PairGame getInstance() {
        if (instance == null) {
            synchronized (PairGame.class) {
                if (instance == null) {
                    instance = new PairGame();
                }
            }
        }
        return instance;
    }

    public boolean isFinished() {
        return solved.size() == elements.size();
    }

    public int getStartTime() {
        return startTime;
    }

    public int getTotalCount() {
        return elements.size();
    }

    public int getSuccessIncrement() {
        return successIncrement;
    }

    public int getTimerPeriod() {
        return timerPeriod;
    }

}
