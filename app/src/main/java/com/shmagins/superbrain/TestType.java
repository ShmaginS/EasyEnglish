package com.shmagins.superbrain;

public enum TestType {
    CALCULATION("CALCULATION"),
    PAIRGAME("PAIRGAME"),
    MEMORYGAME("MEMORYGAME");

    TestType(String tag){
        this.tag = tag;
    }

    public final String tag;

}
