package com.allon.customerview.basekonwageview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.allon.customerview.R;

public class BaseViewKnowledgeActivity extends AppCompatActivity {

    private TextView view_1;

    public static void start(Context context) {
        context.startActivity(new Intent(context, BaseViewKnowledgeActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_view_knowledge);
        view_1 = findViewById(R.id.view_1);

        view_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float x = view_1.getX();
                float y = view_1.getY();
                float translationX = view_1.getTranslationX();
                float translationY = view_1.getTranslationY();
                int top = view_1.getTop();
                int left = view_1.getLeft();
                int right = view_1.getRight();
                int bottom = view_1.getBottom();
                System.out.println("bottom = " + bottom);
            }
        });
    }
}
