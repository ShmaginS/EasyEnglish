package com.shmagins.easyenglish.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.shmagins.easyenglish.AnimalImages;
import com.shmagins.easyenglish.MatrixManager;
import com.shmagins.easyenglish.R;
import com.shmagins.easyenglish.databinding.ActivitySmilesBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class SmilesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySmilesBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_smiles);
        List<Integer> ids = MatrixManager.generateImageIndexList(5, AnimalImages.images.length);
        Observable.zip(
                Observable.fromIterable(ids),
                Observable.interval(0,1500, TimeUnit.MILLISECONDS), (obs, timer) -> obs)
                .subscribe((id) -> runOnUiThread(() -> {
                    binding.imageSmile.clearAnimation();
                    Animation anim = AnimationUtils.loadAnimation(this, R.anim.fadein);
                    binding.imageSmile.setImageResource(AnimalImages.images[id]) ;
                    binding.imageSmile.setAnimation(anim);
                    anim.start();
                }));
    }

    public static Intent getStartIntent(Context context){
        return new Intent(context, SmilesActivity.class);
    }
}
