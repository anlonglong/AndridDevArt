package com.allon.customerview.interceptor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.allon.customerview.R;
import com.allon.customerview.ui.HorizontalScrollViewEx;

import java.util.ArrayList;

public class DemoActivity_1 extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, DemoActivity_1.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_1);
        HorizontalScrollViewEx hsve = findViewById(R.id.hsv);
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        int heightPixels = getResources().getDisplayMetrics().heightPixels;
        LayoutInflater inflater = getLayoutInflater();
        for (int i = 0; i < 3;i++) {
            ViewGroup layout = (ViewGroup) inflater.inflate(
                    R.layout.content_layout, hsve, false);
            layout.getLayoutParams().width = widthPixels;
            TextView textView = layout.findViewById(R.id.title);
            textView.setText("page" + (i +1));
            layout.setBackgroundColor(Color.rgb(255/(i + 1),255/(i + 1), 0));
            createList(layout);
            hsve.addView(layout);
        }
    }

    private void createList(ViewGroup layout) {
        ListView listView = layout.findViewById(R.id.list);
        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            datas.add("name " + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.content_list_item, R.id.name, datas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(DemoActivity_1.this, "click item",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }
}
