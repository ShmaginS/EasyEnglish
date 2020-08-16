package com.shmagins.superbrain.calcgame;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CalcGameLevel {
    @PrimaryKey(autoGenerate = true)
    public int rowid;
    public int lvl;
    public String type;
    public String operations;
    public String firstOperandMin;
    public String firstOperandMax;
    public String secondOperandMin;
    public String secondOperandMax;
    public String resultMin;
    public String resultMax;
}
