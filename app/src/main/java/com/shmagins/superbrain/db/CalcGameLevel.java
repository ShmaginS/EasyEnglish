package com.shmagins.superbrain.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.shmagins.superbrain.Expressions;
import com.shmagins.superbrain.OperationDescriptor;

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
