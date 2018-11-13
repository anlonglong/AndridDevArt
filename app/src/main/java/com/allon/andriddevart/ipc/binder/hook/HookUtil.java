package com.allon.andriddevart.ipc.binder.hook;

import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author anlonglong on 2018/11/13.
 * Email： 940752944@qq.com
 */

/**
 * hook技术就是通过反射，在系统API执行的前后插入自己的业务逻辑代码。
 * https://blog.csdn.net/u012439416/article/details/70477359
 */
@SuppressWarnings("all")
public class HookUtil {

    public static void hookViewListener(View view) {
        try {
            Class<?> viewClazz = Class.forName("android.view.View");
            Method method = viewClazz.getDeclaredMethod("getListenerInfo");
            method.setAccessible(true);
            Object listenerInfoObj = method.invoke(view);
            Class<?> listenerInfo = Class.forName("android.view.View$ListenerInfo");
            Field mOnClickListenerField = listenerInfo.getDeclaredField("mOnClickListener");
            mOnClickListenerField.setAccessible(true);
            final View.OnClickListener m = (View.OnClickListener) mOnClickListenerField.get(listenerInfoObj);
            /**
             * 将listenerInfoObj对象的mOnClickListenerField字段的值改成自己设置的值（这里就是这个View.OnClickListener的匿名内部类）
             * 在匿名内部类中处理完自己的逻辑以后，在调用系统的点击事件。
             *
             */
            mOnClickListenerField.set(listenerInfoObj, new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    Log.i("11111---------","----------");
                    /**
                     * 调用系统的点击事件；
                     * 点击的时候，每次都会调用
                     */
                    m.onClick(v);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
