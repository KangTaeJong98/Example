package com.taetae98.androidcomponent

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MyForegroundService : ScreenStateService() {
    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "com.taetae98.androidcomponent.MyForegroundService"
    }

    override fun onCreate() {
        super.onCreate()
        attachNotificationChannel()
        startForeground()
    }

    private fun attachNotificationChannel() {
        val channel = NotificationChannelCompat.Builder(NOTIFICATION_CHANNEL_ID, NotificationManager.IMPORTANCE_HIGH)
            .setName("Screen State")
            .build()

        val manager = NotificationManagerCompat.from(this)

        manager.createNotificationChannel(channel)
    }

    private fun startForeground() {
        val pendingIntent = Intent(this, MainActivity::class.java).let {
            PendingIntent.getActivity(this, 0, it, PendingIntent.FLAG_IMMUTABLE)
        }

        startForeground(100,
            NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Foreground")
                .setContentText("Running")
                .setContentIntent(pendingIntent)
                .build()
        )
    }
}