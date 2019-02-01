package com.example.databinding;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.Nullable;

public class People {
    public  final ObservableField<String> firstName = new ObservableField<>();

    public final ObservableField<String>  lastName = new ObservableField<>();

    public final ObservableInt age = new ObservableInt();

    public final ObservableField<String> display = new ObservableField<String>(firstName, lastName) {
        @Nullable
        @Override
        public String get() {
            return firstName.get() + lastName.get();
        }
    };
}
