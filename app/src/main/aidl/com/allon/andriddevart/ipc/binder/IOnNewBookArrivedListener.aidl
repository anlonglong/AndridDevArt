// IOnNewBookArrivedListener.aidl
package com.allon.andriddevart.ipc.binder;

import com.allon.andriddevart.ipc.binder.Book;

// Declare any non-default types here with import statements

interface IOnNewBookArrivedListener {

  void onNewBookArrived(in Book newBook);

}
