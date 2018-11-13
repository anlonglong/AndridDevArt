package com.allon.andriddevart.proxy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.allon.andriddevart.R;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, ProxyActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy);
       // proxy();
        try {
            Class<?> activityManagerClass = Class.forName("android.app.ActivityManager");
            Method getService = activityManagerClass.getDeclaredMethod("getService");
            // Object invoke = getServiceMehtod.invoke(managerSingletonObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void proxy() {
        ISay proxyInstance = (ISay) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{ISay.class}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        int length = args.length;
                        System.out.println(" args = "+ args[0]);
                        System.out.println("method = " + method.getName());
                        return null;
                    }
                });
        proxyInstance.say("hello");
    }

    interface ISay {
        void say(String sth);
    }
}
