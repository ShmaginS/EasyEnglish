package com.shmagins.superbrain.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Stats {
    @PrimaryKey(autoGenerate = true)
    public int rowid;
    public int game;
    public int lvl;
    public int stars;
    public int time;
    public int errors;
    public String caption;
}
