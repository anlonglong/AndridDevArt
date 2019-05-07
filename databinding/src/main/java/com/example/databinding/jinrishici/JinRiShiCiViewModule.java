package com.example.databinding.jinrishici;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;

import com.jinrishici.sdk.android.JinrishiciClient;
import com.jinrishici.sdk.android.listener.JinrishiciCallback;
import com.jinrishici.sdk.android.model.JinrishiciRuntimeException;
import com.jinrishici.sdk.android.model.PoetySentence;

public class JinRiShiCiViewModule extends BaseObservable implements JinrishiciCallback {
    public final ObservableField<String> description = new ObservableField<>();

    public JinRiShiCiViewModule() {

    }

    public void startGetJinRiShiCi() {
        JinrishiciClient client = new JinrishiciClient();
        client.getOneSentenceBackground(this);
    }

    @Override
    public void done(PoetySentence poetySentence) {
        setDescription(poetySentence.getData().getContent());
    }

    public void setDescription(String desc) {
        description.set(desc);
        notifyChange();
    }

    @Bindable
    public String getDresription() {
        return description.get();
    }

    @Override
    public void error(JinrishiciRuntimeException e) {

    }
}
