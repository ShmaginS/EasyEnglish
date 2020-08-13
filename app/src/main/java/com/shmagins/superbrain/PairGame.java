package com.shmagins.superbrain;

import android.util.Log;
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
    private int timerPeriod;
    private PublishSubject<Pair<Integer, GameEvent>> events;
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
    private int failed;
    private int additionalPosition;

    public int getFailed() {
        return failed;
    }

    public static class Builder{
        List<Integer> elements;
        int successIncrement;
        int timerPeriod;
        int secondsPerElement;
        private int size;

        public Builder(){
            successIncrement = 1000;
            timerPeriod = -500;
            secondsPerElement = 3;
        }

        public PairGame create() {
            return new PairGame(size, elements, elements.size() * secondsPerElement * 1000, successIncrement, timerPeriod);
        }

        public Builder setElements(List<Integer> elements){
            this.elements = elements;
            return this;
        }


        public Builder setSecondsPerElement(int secondsPerElement) {
            this.secondsPerElement = secondsPerElement;
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

    public void userPressed(int position) {
        selection.add(position);
        events.onNext(new Pair<>(position, GameEvent.SELECT));
        if (selection.size() == 2) {
            if (isSelectionCorrect()) {
                events.onNext(new Pair<>(selection.get(0), GameEvent.SUCCESS));
                events.onNext(new Pair<>(selection.get(1), GameEvent.SUCCESS));
                events.onNext(new Pair<>(0, GameEvent.SOUND));
                solved.add(selection.get(0));
                solved.add(selection.get(1));
                if (elements.size() == size + additionalPosition) {
                    hidden.add(selection.get(0));
                    hidden.add(selection.get(1));
                } else {
                    elements.set(selection.get(0), elements.get(size + additionalPosition++));
                    elements.set(selection.get(1), elements.get(size + additionalPosition++));
                }
                time += successIncrement;
                if (isFinished()) {
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            events.onNext(new Pair<>(time, GameEvent.WIN));
                            stopGame();
                        }
                    }, 500);
                }
            } else {
                failed++;
                events.onNext(new Pair<>(1, GameEvent.SOUND));
                events.onNext(new Pair<>(position, GameEvent.FAIL));
            }
            events.onNext(new Pair<>(selection.get(0), GameEvent.DESELECT));
            events.onNext(new Pair<>(selection.get(1), GameEvent.DESELECT));
            selection.clear();
        }
    }

    private PairGame(int size, List<Integer> elements, int startTime, int successIncrement, int timerPeriod){
        this.successIncrement = successIncrement;
        disposable = new CompositeDisposable();
        events = PublishSubject.create();
        this.timerPeriod = timerPeriod;
        selection = new ArrayList<>();
        this.startTime = startTime;
        solved = new ArrayList<>();
        hidden = new HashSet<>();
        this.elements = elements;
        additionalPosition = 0;
        time = startTime;
        this.size = size;
        failed = 0;
    }

    public void startGame() {
        timer = Observable.interval(timerPeriod, TimeUnit.MILLISECONDS)
                    .subscribe(obs -> {
                        time -= timerPeriod;
                        events.onNext(new Pair<>(time, GameEvent.TIMER));
                    });
    }

    public void pauseGame() {
        timer.dispose();
    }

    public void stopGame() {
        disposable.dispose();
        timer.dispose();
    }

    public void subscribe(Consumer<Pair<Integer, GameEvent>> consumer) {
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

    public int getMaxProgress(){
         return startTime;
    }

    public int getProgressDelta() {
        return -2;
    }

    public int getSize() {
        return size;
    }

    public int getProgress() {
        return time;
    }

    public static class Rules {

        public static int getStarsForResult(int total, int errors, int time) {
            int stars = 3;
            if ((total - errors) * 100 / total < 90)
                stars--;
            if (time < 0)
                stars--;
            return stars;
        }
    }
}
