package com.allon.andriddevart.ipc.manualbinder;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.allon.andriddevart.R;
import com.allon.andriddevart.ipc.binder.BookManagerService;
import com.allon.andriddevart.ipc.binder.Book;
import com.allon.andriddevart.ipc.binder.IBookManager;
import com.allon.andriddevart.ipc.binder.IOnNewBookArrivedListener;

import java.util.List;

public class BookManagerActivity extends AppCompatActivity {
    private static final String TAG = "BookManagerActivity";
    private IBookManager mRemoteBookManager;
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (mRemoteBookManager == null) {
                return;
            }
            mRemoteBookManager.asBinder().unlinkToDeath(this, 0);
            mRemoteBookManager = null;
        }
    };
    private IOnNewBookArrivedListener.Stub mListener;
    private ServiceConnection mConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        mConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                IBookManager bookManager = IBookManager.Stub.asInterface(service);
                mRemoteBookManager = bookManager;
                try {
                    mRemoteBookManager.asBinder().linkToDeath(mDeathRecipient, 0);
                    List<Book> list = bookManager.getBookList();
                    Log.i(TAG, "query book list, list type:"
                            + list.getClass().getCanonicalName());
                    Log.i(TAG, "query book list:" + list.toString());
                    //注定去服务端查询是否有新书插入
                    //queryAddNewBook(bookManager);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mRemoteBookManager = null;
                Log.d(TAG, "onServiceDisconnected. tname:" + Thread.currentThread().getName());
            }
        };
        bindService(new Intent(this, BookManagerService.class), mConnection, BIND_AUTO_CREATE);
    }

    private void queryAddNewBook(IBookManager bookManager) throws RemoteException {
        Book newBook = new Book(3, "Android进阶");
        bookManager.adddBook(newBook);
        Log.i(TAG, "add book:" + newBook);
        List<Book> newList = bookManager.getBookList();
        Log.i(TAG, "query book list:" + newList.toString());
    }

    @Override
    protected void onDestroy() {
        if (mRemoteBookManager != null && mRemoteBookManager.asBinder().isBinderAlive()) {
            try {
                mRemoteBookManager.unRegisterListener(mListener);
                unbindService(mConnection);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }
}
