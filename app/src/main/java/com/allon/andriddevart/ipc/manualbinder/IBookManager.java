package com.allon.andriddevart.ipc.manualbinder;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import com.allon.andriddevart.ipc.binder.Book;

import java.util.List;

/**
 * @author anlonglong on 2018/10/28.
 * Email： 940752944@qq.com
 */
public interface IBookManager extends IInterface {
    String DESCRIPTOR = "com.allon.andriddevart.ipc.manualbinder.IBookManager";
    int TRANSACTION_GET_BOOK_LIST = IBinder.FIRST_CALL_TRANSACTION;
    int TRANSACTION_ADD_BOOK = IBinder.FIRST_CALL_TRANSACTION + 1;
    List<Book> getBookList() throws RemoteException;
    void addBook(Book book) throws RemoteException;
}
