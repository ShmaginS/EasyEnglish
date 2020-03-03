package com.shmagins.easyenglish;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CalculationViewModelFactory implements ViewModelProvider.Factory {
    private Application application;


    public CalculationViewModelFactory(Application application) {
        this.application = application;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new CalculationsViewModel(application);
    }
}