package com.shmagins.easyenglish;

import android.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class PairGame {
    private final boolean timerEnabled;
    private int timerPeriod;
    private PublishSubject<Pair<Integer, Event>> events;
    private CompositeDisposable disposable;
    private List<Integer> selection;
    private List<Integer> elements;
    private List<Integer> solved;
    private Set<Integer> hidden;
    private int successIncrement;
    private Disposable timer;
    private int startTime;
    private int size;
    private int time;

    public static class Builder{
        List<Integer> elements;
        boolean timerEnabled;
        int startTime;
        int successIncrement;
        int timerPeriod;
        private int size;

        public Builder(){
            timerEnabled = true;
            startTime = 10000;
            successIncrement = 1000;
            timerPeriod = 500;
        }

        public PairGame create() {
            return new PairGame(size, elements, timerEnabled, startTime, successIncrement, timerPeriod);
        }

        public Builder setElements(List<Integer> elements){
            this.elements = elements;
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

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }
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

    public void userPressed(int position) {
        selection.add(position);
        events.onNext(new Pair<>(position, Event.SELECT));
        if (selection.size() == 2) {
            if (isSelectionCorrect()) {
                events.onNext(new Pair<>(selection.get(0), Event.SUCCESS));
                events.onNext(new Pair<>(selection.get(1), Event.SUCCESS));
                solved.add(selection.get(0));
                solved.add(selection.get(1));
                hidden.add(selection.get(0));
                hidden.add(selection.get(1));
                time += successIncrement;
                if (isFinished()) {
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            events.onNext(new Pair<>(position, Event.WIN));
                            stopGame();
                        }
                    }, 500);
                }
            } else {
                events.onNext(new Pair<>(position, Event.FAIL));
            }
            events.onNext(new Pair<>(selection.get(0), Event.DESELECT));
            events.onNext(new Pair<>(selection.get(1), Event.DESELECT));
            selection.clear();
        }
    }

    private PairGame(int size, List<Integer> elements,  boolean timerEnabled, int startTime, int successIncrement, int timerPeriod){
        this.successIncrement = successIncrement;
        disposable = new CompositeDisposable();
        events = PublishSubject.create();
        this.timerEnabled = timerEnabled;
        this.timerPeriod = timerPeriod;
        selection = new ArrayList<>();
        this.startTime = startTime;
        solved = new ArrayList<>();
        hidden = new HashSet<>();
        this.elements = elements;
        time = startTime;
        this.size = size;
    }

    public void startGame() {
        if (timerEnabled) {
            timer = Observable.interval(timerPeriod, TimeUnit.MILLISECONDS)
                    .subscribe(obs -> {
                        time -= timerPeriod;
                        if (time >= 0) {
                            events.onNext(new Pair<>(time, Event.TIMER));
                        } else {
                            events.onNext(new Pair<>(0, Event.LOSE));
                            stopGame();
                        }
                    });
        }
    }

    public void pauseGame() {
        if (timerEnabled) {
            timer.dispose();
        }
    }

    public void stopGame() {
        disposable.dispose();
        if (timerEnabled) {
            timer.dispose();
        }
    }

    public void subscribe(Consumer<Pair<Integer, Event>> consumer) {
        disposable.add(events.subscribe(consumer));
    }

    public boolean isPositionSelected(int position) {
        return selection.contains(position);
    }

    private boolean isSelectionCorrect() {
        return elements.get(selection.get(0)).equals(elements.get(selection.get(1))) && !selection.get(0).equals(selection.get(1));
    }

    public boolean isPositionVisible(int position) {
        return !hidden.contains(position);
    }

    public boolean isFinished() {
        return solved.size() == elements.size();
    }

    public int getElement(int position) {
        return elements.get(position);
    }

    public int getSuccessIncrement() {
        return successIncrement;
    }

    public int getTimerPeriod() {
        return timerPeriod;
    }

    public int getTotalCount() {
        return elements.size();
    }

    public int getStartTime() {
        return startTime;
    }

    public int getSize() {
        return size;
    }

    public int getTime() {
        return time;
    }

}
