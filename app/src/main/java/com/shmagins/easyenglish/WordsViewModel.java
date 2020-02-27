package com.shmagins.easyenglish;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.shmagins.easyenglish.db.Calculation;
import com.shmagins.easyenglish.db.CalculationDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class WordsViewModel extends AndroidViewModel {
    @Inject
    CalculationDatabase wdb;
    private WordsRepository repository;

    public WordsViewModel(@NonNull Application application) {
        super(application);
        ((CalculationApplication)application).getApplicationComponent()
                .inject(this);
        repository = new WordsRepository(wdb);

        List<Calculation> testCalculations = new ArrayList<>();
        testCalculations.add(new Calculation("Calculation 1", "Слово 1", "En-Ru", "[ЫЫЫ]",0));
        testCalculations.add(new Calculation("Calculation 2", "Слово 2", "En-Ru", "[ЫЫЫ]", 0));
        testCalculations.add(new Calculation("Calculation 3", "Слово 3", "En-Ru", "[ЫЫЫ]", 0));
        testCalculations.add(new Calculation("Calculation 4", "Слово 4", "En-Ru", "[ЫЫЫ]", 0));
        testCalculations.add(new Calculation("Calculation 5", "Слово 5", "En-Ru", "[ЫЫЫ]", 0));
        testCalculations.add(new Calculation("Calculation 6", "Слово 6", "En-Ru", "[ЫЫЫ]", 0));
        testCalculations.add(new Calculation("Calculation 7", "Слово 7", "En-Ru", "[ЫЫЫ]", 0));
        repository.insertWords(testCalculations);

    }

    public Observable<List<Calculation>> getAll(){
        return wdb.calculationDao().getAll();
    }
}