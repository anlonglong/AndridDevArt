// IBookManager.aidl
package com.allon.andriddevart.ipc.binder;

// Declare any non-default types here with import statements
import com.allon.andriddevart.ipc.binder.Book;
import com.allon.andriddevart.ipc.binder.IOnNewBookArrivedListener;

interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
            List<Book> getBookList();
            void adddBook(in Book book);
            void registerListener(IOnNewBookArrivedListener listener);
            void unRegisterListener(IOnNewBookArrivedListener listener);
}
