package com.example.drawabledemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import static android.animation.ValueAnimator.REVERSE;

public class MainActivity extends AppCompatActivity {

    private ClipDrawable mDrawable;
    private ImageView mImageView;
    private ScaleDrawable mScaleDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView iv = findViewById(R.id.iv);
        mDrawable = (ClipDrawable) iv.getDrawable();

        ObjectAnimator animator = ObjectAnimator.ofInt(this, "percent", 10000, 0);
        animator.setDuration(3000).setRepeatMode(REVERSE);
        animator.addListener(new AnimatorListenerAdapter(){
            @Override
            public void onAnimationEnd(Animator animation) {
                mDrawable.setLevel(10000);
            }
        });
        animator.start();
        mImageView = findViewById(R.id.iv_2);
        mScaleDrawable = (ScaleDrawable) mImageView.getBackground();
        mScaleDrawable.setLevel(10000);
        ObjectAnimator.ofInt(this, "scale", 0, 10000).setDuration(3000).start();

    }

    public void setPercent(int progress) {
        mDrawable.setLevel(progress);
    }

    public void setScale(int progress) {
        mScaleDrawable.setLevel(progress);
    }
}
