package com.example.databinding;

import android.content.Context;
import android.widget.Toast;

public class Presenter {

    private final Context mContext;

    public Presenter(Context context) {
        this.mContext = context;
    }

    public void onSaveClick(User user){
        Toast.makeText(mContext, user.toString(), Toast.LENGTH_SHORT).show();
    }
}
