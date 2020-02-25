package com.shmagins.easyenglish.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Calculation {

    @NonNull
    @PrimaryKey
    public int first;
    public int second;
    public String operation;
    public int result;
}
