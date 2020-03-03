package com.shmagins.easyenglish.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Calculation {
    public Calculation(int first, int second, String operation, int result) {
        this.first = first;
        this.second = second;
        this.operation = operation;
        this.result = result;
    }

    @NonNull
    @PrimaryKey
    public int id;
    public int first;
    public int second;
    public String operation;
    public int result;
}
