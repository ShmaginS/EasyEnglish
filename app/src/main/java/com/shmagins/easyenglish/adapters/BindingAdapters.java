package com.shmagins.easyenglish.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.shmagins.easyenglish.R;

public class BindingAdapters {
    @BindingAdapter("android:src")
    public static void setImageResource(ImageView imageView, int resource){
        imageView.setImageResource(resource);
    }

    @BindingAdapter("android:backgroundColor")
    public static void setImageBackgroundColor(ImageView imageView, String color){
        imageView.setBackgroundColor(Color.parseColor(color));
    }

    @BindingAdapter("android:fadeOut")
    public static void setImageFadeOut(View view, Boolean fadeOut){
        if (fadeOut) {
            Context context = view.getContext();
            Animation anim = AnimationUtils.loadAnimation(context, R.anim.fadeout);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.setAnimation(anim);
        }
    }
}
