package com.shmagins.easyenglish.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.shmagins.easyenglish.R;
import com.shmagins.easyenglish.databinding.ActivitySmilesBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class SmilesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySmilesBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_smiles);
        List<Integer> ids = new ArrayList<>();
        ids.add(R.drawable.animals_puppy);
        ids.add(R.drawable.animals_turtle);
        ids.add(R.drawable.animals_chicken);
        ids.add(R.drawable.animals_whale);
        ids.add(R.drawable.animals_crab);
        Observable.zip(
                Observable.fromIterable(ids),
                Observable.interval(1000, TimeUnit.MILLISECONDS), (obs, timer) -> obs)
                .subscribe((id) -> binding.imageSmile.setImageResource(id));
    }

    public static Intent getStartIntent(Context context){
        return new Intent(context, SmilesActivity.class);
    }
}
