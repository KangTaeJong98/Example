package com.taetae98.androidcomponent

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class ScreenStateService : Service() {
    private var screenStateReceiver: ScreenStateReceiver? = null

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                delay(1500L)
                Toast.makeText(this@ScreenStateService, "Running", Toast.LENGTH_SHORT).show()
                Log.d("PASS", "Running")
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        ScreenStateReceiver().also { receiver ->
            screenStateReceiver = receiver
            registerReceiver(receiver, IntentFilter(Intent.ACTION_SCREEN_ON))
        }

        return START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        super.onDestroy()
        screenStateReceiver?.let { receiver ->
            screenStateReceiver = null
            unregisterReceiver(receiver)
        }
    }
}