package com.example.databinding;

import android.content.Context;
import android.widget.Toast;

public class Presenter {

    private final Context mContext;

    public Presenter(Context context) {
        this.mContext = context;
    }

    public void onSaveClick(User user){
        MainActivity.mDirtyFlags|= 0x02;
        Toast.makeText(mContext, user.toString() + (((MainActivity.mDirtyFlags&0x02) == 0x02) ? "true" : "false") + MainActivity.mDirtyFlags, Toast.LENGTH_SHORT).show();
    }
}
