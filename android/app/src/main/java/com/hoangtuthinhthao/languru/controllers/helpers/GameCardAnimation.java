package com.hoangtuthinhthao.languru.controllers.helpers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.hoangtuthinhthao.languru.R;

public class GameCardAnimation {
    public static void flipBack(final Button btn, final boolean isImage) {
        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(btn, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(btn, "scaleX", 0f, 1f);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());
        oa1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                btn.setBackgroundResource(R.drawable.ic_cell);
                if(!isImage) {
                    btn.setText("");
                }
                oa2.start();
            }
        });
        oa1.start();
    }
    public static void flipImage(final Context context, final Button btn, final Bitmap imgResource) {
        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(btn, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(btn, "scaleX", 0f, 1f);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());
        oa1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                BitmapDrawable ob = new BitmapDrawable(context.getResources(), imgResource);
                btn.setBackground(ob);
                oa2.start();
            }
        });
        oa1.start();
    }


    public static void flipWord(final Button btn, final String label) {
        final ObjectAnimator oa3 = ObjectAnimator.ofFloat(btn, "scaleX", 1f, 0f);
        final ObjectAnimator oa4 = ObjectAnimator.ofFloat(btn, "scaleX", 0f, 1f);
        oa3.setInterpolator(new DecelerateInterpolator());
        oa4.setInterpolator(new AccelerateDecelerateInterpolator());
        oa3.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                btn.setText(label);
                btn.setBackgroundColor(Color.WHITE);
                oa4.start();
            }
        });
        oa3.start();
    }
}
