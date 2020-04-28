package com.shmagins.superbrain.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class HistoryRecord {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String test;
    public int elapsedTime;
    public long date;
    public String info;
}
