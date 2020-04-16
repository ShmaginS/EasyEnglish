package com.shmagins.easyenglish;

import android.util.Pair;

import java.util.List;

import io.reactivex.subjects.PublishSubject;

public class MemoryGame {
    private PublishSubject<Pair<Integer, Event>> events;
    private List<Integer> elements;
    private List<Integer> variants;

    public enum Event {
        SELECT,
        DESELECT,
        SUCCESS,
        TIMER,
        FAIL,
        WIN,
        LOSE
    }
}
