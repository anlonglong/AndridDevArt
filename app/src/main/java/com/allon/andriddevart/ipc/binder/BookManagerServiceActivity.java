package com.allon.andriddevart.ipc.binder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.allon.andriddevart.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class BookManagerServiceActivity extends AppCompatActivity {

    private IBookManager mRemoteBookManager;
    private TextView mTv;
    private static final String  TAG = BookManagerServiceActivity.class.getSimpleName();

    public static void start(Context context) {
        context.startActivity(new Intent(context, BookManagerServiceActivity.class));
    }


    /**
     * hook技术就是通过反射，在系统API执行的前后插入自己的业务逻辑代码。
     */
    public void hook(){
        try {
            Class<?> view = Class.forName("android.view.View");
            Method method = view.getDeclaredMethod("getListenerInfo");
            method.setAccessible(true);
            Object listenerInfoObj = method.invoke(mTv);
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
            View.OnClickListener proxyInstance = (View.OnClickListener) Proxy.newProxyInstance(mTv.getClass().getClassLoader(), new Class[]{View.OnClickListener.class}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    //首次点击的时候调用。
                    Log.i("11111---------","----------");
                    return method.invoke(m, args);
                }
            });
            proxyInstance.onClick(mTv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private IOnNewBookArrivedListener.Stub mListener;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mRemoteBookManager = IBookManager.Stub.asInterface(service);
            try {
                List<Book> bookList = mRemoteBookManager.getBookList();
                Log.i(TAG, bookList.toString());
                Log.i(TAG, bookList.getClass().getCanonicalName());
                mRemoteBookManager.adddBook(new Book(3, "大话数据结构"));
                bookList = mRemoteBookManager.getBookList();
                Log.i(TAG, bookList.toString());
                mListener = new IOnNewBookArrivedListener.Stub() {
                    @Override
                    public void onNewBookArrived(Book newBook) throws RemoteException {
                        Log.e(TAG, newBook.toString());
                        Log.e(TAG, Thread.currentThread().getName());
                        //这里是子线程，如果想跟新UI，请把数据发送到UI线程中去。
                    }
                };
                Log.e(TAG, mListener.toString());
                mRemoteBookManager.registerListener(mListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected=====");
            Intent intent = new Intent(BookManagerServiceActivity.this, BookManagerService.class);
            bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager_service);
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        mTv = findViewById(R.id.textView);
        mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("22222==========","============");
            }
        });
        hook();
    }

    @Override
    protected void onDestroy() {
        if (mRemoteBookManager != null && mRemoteBookManager.asBinder().isBinderAlive()) {
            try {
                mRemoteBookManager.unRegisterListener(mListener);
                unbindService(mServiceConnection);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }
}
