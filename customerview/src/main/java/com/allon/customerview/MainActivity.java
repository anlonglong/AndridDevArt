package com.allon.customerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.allon.customerview.basekonwageview.BaseViewKnowledgeActivity;
import com.allon.customerview.interceptor.DemoActivity_1;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_base_view_knowladge).setOnClickListener(this);
        findViewById(R.id.tv_interceptor).setOnClickListener(this);
        findViewById(R.id.tv_circle_view).setOnClickListener(this);
        findViewById(R.id.tv_reveal_view).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_base_view_knowladge:
                BaseViewKnowledgeActivity.start(this);
                break;
            case R.id.tv_interceptor:
                DemoActivity_1.start(v.getContext());
                break;
            case R.id.tv_circle_view:
                CircleViewActivity.start(v.getContext());
                case R.id.tv_reveal_view:
                RevealLayoutActivity.start(v.getContext());
                break;
                default:
        }
    }

}
