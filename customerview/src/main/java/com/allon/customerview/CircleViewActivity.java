package com.allon.customerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CircleViewActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, CircleViewActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_view);

    }
}
