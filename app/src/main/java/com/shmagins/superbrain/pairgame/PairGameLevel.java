package com.shmagins.superbrain.pairgame;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PairGameLevel {
    @PrimaryKey(autoGenerate = true)
    public int rowid;
    public int lvl;
    public int num;
    public int inc;
    public int width;
    public int height;
    public int screens;
}
