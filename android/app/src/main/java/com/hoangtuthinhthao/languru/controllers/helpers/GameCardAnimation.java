package com.hoangtuthinhthao.languru.controllers.helpers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.hoangtuthinhthao.languru.R;

public class GameCardAnimation {
    public static void flip(final ImageView img, final int imgResource) {
        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(img, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(img, "scaleX", 0f, 1f);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());
        oa1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                img.setImageResource(imgResource);
                oa2.start();
            }
        });
        oa1.start();
    }
    public static void flip(final Button btn, final int imgResource) {
        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(btn, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(btn, "scaleX", 0f, 1f);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());
        oa1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                btn.setBackgroundResource(imgResource);
                oa2.start();
            }
        });
        oa1.start();
    }
}
