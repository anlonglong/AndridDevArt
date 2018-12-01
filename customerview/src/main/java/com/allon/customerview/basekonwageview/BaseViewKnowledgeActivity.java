package com.allon.customerview.basekonwageview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;
import android.widget.TextView;

import com.allon.customerview.R;

public class BaseViewKnowledgeActivity extends AppCompatActivity implements View.OnTouchListener {

    private TextView view_1;
    private VelocityTracker velocityTracker;
    Scroller mScroller;

    public static void start(Context context) {
        context.startActivity(new Intent(context, BaseViewKnowledgeActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_view_knowledge);
        view_1 = findViewById(R.id.view_1);
        view_1.setOnTouchListener(this);
        mScroller =  new Scroller(this);
        velocityTracker = VelocityTracker.obtain();
        velocityTracker.computeCurrentVelocity(100);
        ViewConfiguration.get(this).getScaledTouchSlop();
        final CustomerView cv = findViewById(R.id.cv);
        view_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("++++++++++++++","click listener");

                cv.start();
                float x = view_1.getX();
                float y = view_1.getY();
                float translationX = view_1.getTranslationX();
                float translationY = view_1.getTranslationY();
                int top = view_1.getTop();
                int left = view_1.getLeft();
                int right = view_1.getRight();
                int bottom = view_1.getBottom();
                System.out.println("bottom = " + bottom);
                mScroller.startScroll(0, 0, 100, 100, 1000);
            }
        });
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        velocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("++++++++++++++","DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("++++++++++++++","MOVE");

                float xVelocity = velocityTracker.getXVelocity();
                float yVelocity = velocityTracker.getYVelocity();
                Log.i("-------","yVelocity = " + yVelocity);
                Log.i("-------","xVelocity = " + xVelocity);
                break;
                case MotionEvent.ACTION_UP:
                    Log.i("++++++++++++++","UP");
                    break;
        }
        return false;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("++++++++++++++","dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onUserInteraction() {
        Log.i("++++++++++++++","onUserInteraction");

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("++++++++++++++","activity onTouchEvent");
        return super.onTouchEvent(event);
    }
}
