package com.example.databinding;

import android.view.View;
import android.widget.Toast;

import com.example.databinding.jinrishici.JinRiShiCiActivity;

public class MyHandler {

    public void onClickFriend(View view) {
        MainActivity.mDirtyFlags|= 0x01;
        Toast.makeText(view.getContext(), "hahahah = " +  (((MainActivity.mDirtyFlags&0x01) == 0x01) ? "true" : "false")+ MainActivity.mDirtyFlags, Toast.LENGTH_SHORT).show();
        JinRiShiCiActivity.start(view.getContext());
    }
}
