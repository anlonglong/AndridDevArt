package com.example.databinding.jinrishici;

import com.jinrishici.sdk.android.model.JinrishiciRuntimeException;
import com.jinrishici.sdk.android.model.PoetySentence;

public interface JinRiShiCiNavigation {


    public void done(PoetySentence poetySentence);


    public void error(JinrishiciRuntimeException e);

}
