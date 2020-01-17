package com.shmagins.easyenglish.model;

public class Word {
    public String word;
    public String translation;
    public String languageCode;
    public boolean isDirectTranslation;
    public int difficultyLevel;

    public Word(String word, String translation, String languageCode, boolean isDirectTranslation, int difficultyLevel) {
        this.word = word;
        this.translation = translation;
        this.languageCode = languageCode;
        this.isDirectTranslation = isDirectTranslation;
        this.difficultyLevel = difficultyLevel;
    }
}
