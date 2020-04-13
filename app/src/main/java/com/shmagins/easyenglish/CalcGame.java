package com.shmagins.easyenglish;

import android.os.SystemClock;
import android.util.Pair;

import com.shmagins.easyenglish.db.Calculation;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class CalcGame {
    private List<Calculation> calculations;
    private int milliseconds = 0;
    private long startTime = 0;
    private PublishSubject<Pair<Integer, Event>> events;
    private List<Calculation> solved;
    private List<Calculation> failed;
    private CompositeDisposable disposable;

    public int rightCount() {
        return solved.size();
    }

    public int wrongCount() {
        return failed.size();
    }

    public enum Event {
        UPDATE,
        WIN
    }

    public void subscribe(Consumer<Pair<Integer, Event>> consumer) {
        disposable.add(events.subscribe(consumer));
    }

    public Calculation getCalculation(int i) {
        return calculations.get(i);
    }

    public int getCalculationCount() {
        return calculations.size();
    }

    public CalcGame() {
        this.calculations = new ArrayList<>();
        events = PublishSubject.create();
        solved = new ArrayList<>();
        failed = new ArrayList<>();
        disposable = new CompositeDisposable();
    }

    public void setCalculations(List<Calculation> calculations) {
        this.calculations = calculations;
    }

    public void startGame() {
        startTime = SystemClock.elapsedRealtime();
    }

    public void pauseGame() {
        milliseconds += SystemClock.elapsedRealtime() - startTime;
    }

    public void answer(int i, int answer) {
        Calculation calc = calculations.get(i);
        calc.answer = answer;
        events.onNext(new Pair<>(i, Event.UPDATE));
        if (calc.result == answer) {
            solved.add(calc);
        } else {
            failed.add(calc);
        }
        if (isFinished()) {
            milliseconds += SystemClock.elapsedRealtime() - startTime;
            events.onNext(new Pair<>(milliseconds, Event.WIN));
            disposable.dispose();
        }
    }

    public boolean isFinished() {
        return solved.size() + failed.size() == calculations.size();
    }

}
