package com.example.databinding.jinrishici;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.databinding.R;
import com.example.databinding.databinding.JinRiShiFragmentBinding;

public class JinRiShiCiFragment extends Fragment {

    private JinRiShiCiViewModule mViewModel;

    public static Fragment getInstance() {
        return new JinRiShiCiFragment();
    }



    public void setViewModule(JinRiShiCiViewModule jinRiShiCiViewModule) {
        this.mViewModel = jinRiShiCiViewModule;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jin_ri_shi_fragment, container, false);
        JinRiShiFragmentBinding bind = JinRiShiFragmentBinding.bind(view);
        bind.setViewModule(mViewModel);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.startGetJinRiShiCi();
    }
}
