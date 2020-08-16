package com.shmagins.superbrain.common;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.shmagins.superbrain.R;

public class BindingAdapters {
    @BindingAdapter("android:src")
    public static void setImageResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

    @BindingAdapter("android:src")
    public static void setImageDrawable(ImageView imageView, Drawable drawable) {
        imageView.setClipToOutline(true);
        imageView.setImageDrawable(drawable);
    }

    @BindingAdapter("android:backgroundColor")
    public static void setImageBackgroundColor(ImageView imageView, String color) {
        imageView.setBackgroundColor(Color.parseColor(color));
    }

    @BindingAdapter("android:fadeOut")
    public static void setFadeOut(View view, Boolean fadeOut) {
        if (fadeOut) {
            Context context = view.getContext();
            Animation anim = AnimationUtils.loadAnimation(context, R.anim.fadeout);
            ObjectAnimator viewAnim = ObjectAnimator.ofFloat(view, "rotationY", 0f, 180f);
            viewAnim.setDuration(1500);
            viewAnim.setInterpolator(new AccelerateDecelerateInterpolator());
            viewAnim.start();
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

    @BindingAdapter("android:closed")
    public static void setClosed(View view, Boolean closed) {
        float fromX = closed ? 1.0f : 0.9f;
        float toX = closed ? 0.9f : 1f;
        float fromY = closed ? 1.0f : 0.9f;
        float toY = closed ? 0.9f : 1.0f;
        Animation anim = new ScaleAnimation(fromX, toX, fromY, toY, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(1000);
        view.setAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setScaleX(toX);
                view.setScaleY(toY);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
