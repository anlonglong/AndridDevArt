package com.allon.andriddevart

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.allon.andriddevart.ipc.binder.BookManagerServiceActivity
import com.allon.andriddevart.messager.MessagerService
import com.allon.andriddevart.proxy.ProxyActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    inner  class MessagerHandler : Handler() {
        override fun handleMessage(msg: Message) {
            when(msg.what) {
                2 ->{
                    println(msg.data.getString("reply"))
                }
            }
            super.handleMessage(msg)
        }
    }

    private val getReplyMsger = Messenger(MessagerHandler())

    private val mConnection = object : ServiceConnection{
        override fun onServiceDisconnected(name: ComponentName?) {
           println("onServiceDisconnected")
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder) {
                println("onServiceConnected")
                val  mServices = Messenger(service)
                val message = Message.obtain(null, 1)
                val data = Bundle()
                data.putString("msg" ,"hello from client")
                message.data = data
                message.obj
                message.replyTo = getReplyMsger
                println(Process.myPid())
                try {
                    mServices.send(message)
                }catch (e: RemoteException) {
                    e.printStackTrace()
                }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, MessagerService::class.java)
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
        findViewById<TextView>(R.id.bms).setOnClickListener { BookManagerServiceActivity.start(it.context) }
        proxy.setOnClickListener { ProxyActivity.start(it.context) }
    }


    override fun onDestroy() {
        unbindService(mConnection);
        super.onDestroy()
    }
}
