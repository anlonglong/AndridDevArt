package com.example.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.databinding.databinding.ActivityMainBinding;
import com.jinrishici.sdk.android.JinrishiciClient;
import com.jinrishici.sdk.android.listener.JinrishiciCallback;
import com.jinrishici.sdk.android.model.JinrishiciRuntimeException;
import com.jinrishici.sdk.android.model.PoetySentence;

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

        ObservableArrayMap<String, Object> user = new ObservableArrayMap<>();
        user.put("firstName", "Google");
        user.put("lastName", "Inc.");
        user.put("age", 17);
        dataBinding.setArrayMap(user);
        ObservableArrayList<Object> list = new ObservableArrayList<>();
        list.add("Google");
        list.add("Inc.");
        list.add(17);
        dataBinding.setOlist(list);
        JinrishiciClient client = new JinrishiciClient();
        client.getOneSentenceBackground(new JinrishiciCallback() {
            @Override
            public void done(PoetySentence poetySentence) {
                Toast.makeText(MainActivity.this, poetySentence.getData().getContent(), Toast.LENGTH_SHORT).show();
                people.firstName.set(poetySentence.getData().getContent());
            }

            @Override
            public void error(JinrishiciRuntimeException e) {

            }
        });
    }

    public void show() {
        Toast.makeText(this, "show()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "aaaaaaaaa", Toast.LENGTH_SHORT).show();
    }

}
