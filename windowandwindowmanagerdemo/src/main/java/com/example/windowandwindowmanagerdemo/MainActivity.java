package com.example.windowandwindowmanagerdemo;

import android.content.Context;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private WindowManager.LayoutParams mParams;
    private WindowManager mWindowManager;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView imageView = findViewById(R.id.iv);
        findViewById(R.id.tv_add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addButtonUseWindow();
            }
        });

    }

    private void addButtonUseWindow() {
        if (null != mButton) return;
        mButton = new Button(getBaseContext());
        mButton.setText("button");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "lllll", Toast.LENGTH_SHORT).show();
            }
        });
        mButton.setOnTouchListener(this);
        mParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, 1, 0, PixelFormat.TRANSPARENT);
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        mParams.gravity = Gravity.TOP | Gravity.LEFT;
        mParams.x = 100;
        mParams.y = 300;
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.addView(mButton, mParams);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mParams.x = rawX;
                mParams.y = rawY;
                mWindowManager.updateViewLayout(mButton, mParams);
                break;
        }
        return false;
    }
}
