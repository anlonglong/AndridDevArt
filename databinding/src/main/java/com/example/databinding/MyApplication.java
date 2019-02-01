package com.example.databinding;

import android.app.Application;

import com.jinrishici.sdk.android.factory.JinrishiciFactory;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JinrishiciFactory.init(this);
    }
}
