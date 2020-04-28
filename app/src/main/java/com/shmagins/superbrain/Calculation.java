package com.shmagins.superbrain;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class Calculation {

    public Calculation(int id, int first, int second, String operation, int result) {
        this.id = id;
        this.first = first;
        this.second = second;
        this.operation = operation;
        this.result = result;
        this.answer = Integer.MIN_VALUE;
        this.solved = false;
    }

    @NonNull
    @Override
    public String toString() {
        return "" + first + " " + operation + " " + second + " = " + result;
    }

    public int id;
    public int first;
    public int second;
    public String operation;
    public int result;
    public int answer;
    boolean solved;
}
