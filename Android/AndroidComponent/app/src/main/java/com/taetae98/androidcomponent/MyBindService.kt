package com.taetae98.androidcomponent

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyBindService : Service() {
    private var value = 0

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                delay(1500L)
                value++
            }
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return object : Binder(), MyBindInterface {
            override fun getValue(): Int {
                return value
            }
        }
    }

    private interface MyBindInterface {
        fun getValue(): Int
    }

    open class MyBindConnection : ServiceConnection {
        private var binder: MyBindInterface? = null

        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
            if (binder is MyBindInterface) {
                this.binder = binder
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {

        }

        fun getValue(): Int {
            return binder!!.getValue()
        }
    }
}