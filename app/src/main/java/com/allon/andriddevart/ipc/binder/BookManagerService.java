package com.allon.andriddevart.ipc.binder;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings("all")
public class BookManagerService extends Service {

    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<Book>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mListener = new RemoteCallbackList<>();

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void adddBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if (!mListener.contains(listener)) {
//                mListener.add(listener);
//                Log.i("=====","RegisterListener success");
//            }else {
//                Log.i("=====","already exist");
//            }
//            Log.i("=====","Listener size = "+mListener.size());
            mListener.register(listener);
            Log.i("=====","Listener size = "+ mListener.getRegisteredCallbackCount());
            Log.e("////////////service", listener.toString());
        }

        @Override
        public void unRegisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
//           if (mListener.contains(listener)) {
//               mListener.remove(listener);
//               Log.i("=====","unRegisterListener success");
//           }else {
//               Log.i("=====","unRegisterListener not found");
//           }
//            Log.i("=====","Listener size = "+mListener.size());
            mListener.unregister(listener);
            Log.i("=====","Listener size = "+ mListener.getRegisteredCallbackCount());
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1, "Android"));
        mBookList.add(new Book(2, "IOS"));
        new Thread(new ServiceWorker()).start();
    }

    public BookManagerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        //检查是否有连接远程服务的权限
        int check = checkCallingOrSelfPermission("com.allon.andriddevart.ipc.binder.permisson.access.book.service");
        if (check == PackageManager.PERMISSION_DENIED) {
            return null;
        }
        return mBinder;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed.set(true);
        super.onDestroy();
    }

    private class ServiceWorker implements Runnable {
        @Override
        public void run() {
            while (!mIsServiceDestoryed.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = mBookList.size() + 1;
                Book newBook = new Book(bookId, "new book#" + bookId);
                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onNewBookArrived(final Book newBook) throws RemoteException {
        mBookList.add(newBook);
//        for (int i = 0; i < mListener.size(); i++) {
//            IOnNewBookArrivedListener listener = mListener.get(i);
//            try {
//                listener.onNewBookArrived(newBook);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//        }
        int N = mListener.beginBroadcast();
        for (int i = 0 ; i < N; i++) {
            IOnNewBookArrivedListener broadcastItem = mListener.getBroadcastItem(i);
            if (null != broadcastItem) {
                try {
                    broadcastItem.onNewBookArrived(newBook);
                }catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        mListener.finishBroadcast();
    }

}
