package com.example.databinding.jinrishici;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class ActivityUtils {

    public static void addFragment2Activity(FragmentManager fragmentManager, Fragment fragment, int frameId) {
           fragmentManager.beginTransaction().add(frameId, fragment).commit();
    }

}
