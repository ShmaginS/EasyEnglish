package com.shmagins.easyenglish.db;

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
    public Date date;
    public String info;
}
