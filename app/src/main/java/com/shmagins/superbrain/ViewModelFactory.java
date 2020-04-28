package com.shmagins.superbrain;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private Application application;


    public ViewModelFactory(Application application) {
        this.application = application;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if(modelClass == CalcViewModel.class) {
            return (T) new CalcViewModel(application);
        } else {
            throw new IllegalArgumentException("Неизвестный класс");
        }
    }
}