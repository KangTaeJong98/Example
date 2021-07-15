package com.taetae98.notification.manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.taetae98.notification.R
import com.taetae98.notification.activity.MainActivity
import com.taetae98.notification.dto.Message
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NormalNotificationManager @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    companion object {
        private const val CHANNEL_ID = "com.taetae98.notification.NORMAL"
        private const val CHANNEL_NAME = "Normal"
        private const val CHANNEL_DESCRIPTION = "Normal Notification"
    }

    private val manager by lazy { context.getSystemService(NotificationManager::class.java) }

    fun notify(message: Message) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }


        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_android) // Icon
            .setContentTitle(message.title) // Title
            .setContentText(message.message) // Content
            .setPriority(NotificationCompat.PRIORITY_MAX) // 우선순위 (Android 7.1 이하에서 작동, Android 8.0 이상의 경우 Channel 우선순위 적용)
            .build()

        manager.notify(message.id, notification)
    }

    fun cancel(message: Message) {
        manager.cancel(message.id)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH).apply {
            description = CHANNEL_DESCRIPTION
        }

        manager.createNotificationChannel(channel)
    }
}