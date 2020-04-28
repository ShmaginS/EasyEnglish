package com.shmagins.superbrain;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class CalcGame {
    private List<Calculation> calculations;
    private int milliseconds = 0;
    private long startTime = 0;
    private PublishSubject<Pair<Integer, GameEvent>> events;
    private Set<Integer> solved;
    private Set<Integer> failed;
    private CompositeDisposable disposable;

    public int rightCount() {
        return solved.size();
    }

    public int wrongCount() {
        return failed.size();
    }


    public void subscribe(Consumer<Pair<Integer, GameEvent>> consumer) {
        disposable.add(events.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer));
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
        solved = new HashSet<>();
        failed = new HashSet<>();
        disposable = new CompositeDisposable();
    }

    public void setCalculations(List<Calculation> calculations) {
        Log.d("happy", "setCalculations " + calculations);
        this.calculations = calculations;
        events.onNext(new Pair<>(calculations.size(), GameEvent.UPDATE_ALL));
    }

    public void startGame() {
        startTime = SystemClock.elapsedRealtime();
    }

    public void pauseGame() {
        milliseconds += SystemClock.elapsedRealtime() - startTime;
    }

    public void answer(int i, int answer) {
        Log.d("happy", "answer: " + i + " " + answer);
        Calculation calc = calculations.get(i);
        calc.answer = answer;
        events.onNext(new Pair<>(i, GameEvent.UPDATE));
        if (calc.result == answer) {
            solved.add(i);
        } else {
            failed.add(i);
        }
        if (isFinished()) {
            milliseconds += SystemClock.elapsedRealtime() - startTime;
            events.onNext(new Pair<>(milliseconds, GameEvent.WIN));
            new Handler()
                    .postDelayed(() -> {
                        disposable.dispose();
                    }, 2000);
        }
    }

    public boolean isFinished() {
        Log.d("happy", "isFinished: " + solved.size() + " " + failed.size() + " " + calculations.size());
        return solved.size() + failed.size() == calculations.size();
    }

}
