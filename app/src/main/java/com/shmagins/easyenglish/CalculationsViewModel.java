package com.shmagins.easyenglish;

import android.app.Application;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.shmagins.easyenglish.db.Calculation;
import com.shmagins.easyenglish.db.CalculationDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CalculationsViewModel extends AndroidViewModel {
    @Inject
    CalculationDatabase wdb;
    private WordsRepository repository;

    public CalculationsViewModel(@NonNull Application application) {
        super(application);
        ((CalculationApplication)application).getApplicationComponent()
                .inject(this);
        repository = new WordsRepository(wdb);
        repository.deleteAll();
        List<Calculation> testCalculations = new ArrayList<>();
        testCalculations.add(new Calculation(2,2,"*", 4));
        testCalculations.add(new Calculation(1,3,"+", 4));
        testCalculations.add(new Calculation(3,1,"-", 2));
        testCalculations.add(new Calculation(56,7,"/", 8));
        repository.insertWords(testCalculations);

    }

    public Observable<List<Calculation>> getAll(){
        return repository.getAllWords();
    }

    public void updateCalculation(Calculation calculation){
        repository.updateWord(calculation);
    }

}