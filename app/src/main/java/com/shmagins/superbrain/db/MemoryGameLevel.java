package com.shmagins.superbrain.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MemoryGameLevel {
    @PrimaryKey(autoGenerate = true)
    public int rowid;
    public int lvl;
    public int usedImages;
    public int gameFieldWidth;
    public int gameFieldHeight;
}
