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

import com.allon.andriddevart.R;

import java.util.List;

public class BookManagerServiceActivity extends AppCompatActivity {

    private IBookManager mRemoteBookManager;

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
                Log.i("BookManager", bookList.toString());
                Log.i("BookManager", bookList.getClass().getCanonicalName());
                mRemoteBookManager.adddBook(new Book(3, "大话数据结构"));
                bookList = mRemoteBookManager.getBookList();
                Log.i("BookManager", bookList.toString());
                mListener = new IOnNewBookArrivedListener.Stub() {
                    @Override
                    public void onNewBookArrived(Book newBook) throws RemoteException {
                        Log.e("=========", newBook.toString());
                    }
                };
                mRemoteBookManager.registerListener(mListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager_service);
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
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
