package com.allon.customerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.allon.customerview.basekonwageview.BaseViewKnowledgeActivity;

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_base_view_knowladge:
                BaseViewKnowledgeActivity.start(this);
                OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
                Retrofit retrofit = new Retrofit.Builder().
                        addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient).baseUrl("http://118.190.86.237/fireInspection/php/")
                        .build();
                LoginParams loginParams = new LoginParams();
                loginParams.setPassword("96e79218965eb72c92a549dd5a330112");
                loginParams.setName("13333333333");
                retrofit.create(ApiService.class).method(loginParams.getUrl(), loginParams).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        System.out.println("call = [" + call + "], response = [" + response + "]");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        System.out.println("call = [" + call + "], t = [" + t + "]");
                    }
                });
                break;
                default:
        }
    }

    public interface ApiService {
        @POST
        public Call<ResponseBody> method(@Url String url, @Body LoginParams loginParams);
    }
}
