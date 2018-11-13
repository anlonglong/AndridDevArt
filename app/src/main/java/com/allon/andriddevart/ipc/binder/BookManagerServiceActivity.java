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
import com.allon.andriddevart.ipc.binder.hook.HookUtil;

import java.util.List;

public class BookManagerServiceActivity extends AppCompatActivity {

    private IBookManager mRemoteBookManager;
    private TextView mTv;
    private static final String  TAG = BookManagerServiceActivity.class.getSimpleName();

    public static void start(Context context) {
        context.startActivity(new Intent(context, BookManagerServiceActivity.class));
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
        HookUtil.hookViewListener(mTv);
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
