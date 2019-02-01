package com.example.databinding;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.databinding.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static  long mDirtyFlags = 0xffL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityMainBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        User jack = new User("jack", 18);
        dataBinding.setUser(jack);
        dataBinding.setStr("sssssssssssss");
        ArrayList<String> strings = new ArrayList<>();
        strings.add("listValue");
        dataBinding.setList(strings);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("key", "mapValue");
        dataBinding.setMap(hashMap);
        dataBinding.setClickListener(this);
        dataBinding.setMyHandler(new MyHandler());
        dataBinding.setPresenter(new Presenter(this));
        final People people = new People();
        people.firstName.set("alex");
        people.lastName.set("tomas");
        dataBinding.setPeople(people);
        dataBinding.tv.postDelayed(new Runnable() {
            @Override
            public void run() {
                people.firstName.set("alllllllllex");
            }
        }, 3000);

    }

    public void show() {
        Toast.makeText(this, "show()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "aaaaaaaaa", Toast.LENGTH_SHORT).show();
    }

}