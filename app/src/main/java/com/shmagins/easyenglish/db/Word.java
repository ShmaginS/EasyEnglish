package com.shmagins.easyenglish.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Word {
    public Word(@NonNull String word, String translation, String languageCode, String pronunciation, int difficultyLevel) {
        this.word = word;
        this.translation = translation;
        this.languageCode = languageCode;
        this.pronunciation = pronunciation;
        this.difficultyLevel = difficultyLevel;
    }

    @NonNull
    @PrimaryKey
    public String word;
    public String translation;
    public String languageCode;
    public String pronunciation;
    public int difficultyLevel;
}
