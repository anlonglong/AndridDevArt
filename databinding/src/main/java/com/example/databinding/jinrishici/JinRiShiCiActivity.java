package com.example.databinding.jinrishici;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.databinding.R;
import com.jinrishici.sdk.android.model.JinrishiciRuntimeException;
import com.jinrishici.sdk.android.model.PoetySentence;

public class JinRiShiCiActivity extends AppCompatActivity implements JinRiShiCiNavigation{

    public static void start(Context context) {
        context.startActivity(new Intent(context, JinRiShiCiActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jin_ri_shi_ci);
        JinRiShiCiFragment fragment = (JinRiShiCiFragment) findFragmentOrCreate();
        fragment.setViewModule(new JinRiShiCiViewModule());
    }

    Fragment findFragmentOrCreate() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (fragment == null ) {
            fragment = JinRiShiCiFragment.getInstance();
        }
        ActivityUtils.addFragment2Activity(getSupportFragmentManager(), fragment, R.id.contentFrame);
        return fragment;
    }

    @Override
    public void done(PoetySentence poetySentence) {

    }

    @Override
    public void error(JinrishiciRuntimeException e) {

    }
}
