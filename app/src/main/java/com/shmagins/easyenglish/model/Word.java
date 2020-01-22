package com.shmagins.easyenglish.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Word {
    public Word(String word, String translation, String languageCode, int difficultyLevel) {
        this.word = word;
        this.translation = translation;
        this.languageCode = languageCode;
        this.difficultyLevel = difficultyLevel;
    }

    @NonNull
    @PrimaryKey
    public String word;
    public String translation;
    public String languageCode;
    public int difficultyLevel;
}
