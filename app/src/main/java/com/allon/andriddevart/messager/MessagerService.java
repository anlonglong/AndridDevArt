package com.allon.andriddevart.messager;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

public class MessagerService extends Service {
    public MessagerService() {
    }

    private static class MessagerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String msg1 = msg.getData().getString("msg");
                    Log.i("======",msg1 + Process.myPid());
                    Messenger replyToMessager = msg.replyTo;
                    Message m =Message.obtain(null, 2);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply","我是服务端，我收到你的消息，稍后回复你");
                    m.setData(bundle);
                    try {
                        replyToMessager.send(m);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                    default: super.handleMessage(msg);
            }

        }
    }

    private final Messenger mMessenger = new Messenger(new MessagerHandler());
    @Override
    public IBinder onBind(Intent intent) {
       return mMessenger.getBinder();
    }
}
