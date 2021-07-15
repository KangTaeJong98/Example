package com.taetae98.notification.manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.taetae98.notification.R
import com.taetae98.notification.dto.Message
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExtendNotificationManager @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    companion object {
        private const val CHANNEL_ID_IMPORTANCE_HIGH = "com.taetae98.notification.EXTEND.IMPORTANCE_HIGH"
        private const val CHANNEL_ID_IMPORTANCE_LOW = "com.taetae98.notification.EXTEND.IMPORTANCE_LOW"
        private const val CHANNEL_NAME = "Extend"
        private const val CHANNEL_DESCRIPTION = "Extend Notification"
    }

    private val manager by lazy { context.getSystemService(NotificationManager::class.java) }

    fun imageNotify(message: Message, bitmap: Bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(CHANNEL_ID_IMPORTANCE_HIGH, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID_IMPORTANCE_HIGH)
            .setSmallIcon(R.drawable.ic_android)
            .setContentTitle(message.title)
            .setContentText(message.message)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(bitmap)
            )
            .build()

        manager.notify(message.id, notification)
    }

    fun progressNotify(message: Message) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(CHANNEL_ID_IMPORTANCE_HIGH, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            createNotificationChannel(CHANNEL_ID_IMPORTANCE_LOW, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW)
        }

        CoroutineScope(Dispatchers.IO).launch {
            repeat(101) {
                val notification = NotificationCompat.Builder(context, if(it == 100) CHANNEL_ID_IMPORTANCE_HIGH else CHANNEL_ID_IMPORTANCE_LOW)
                    .setPriority(if (it == 100) NotificationCompat.PRIORITY_MAX else NotificationCompat.PRIORITY_MIN)
                    .setSmallIcon(R.drawable.ic_android)
                    .setContentTitle(message.title)
                    .setContentText(message.message)
                    .setProgress(100, it, it <= 10)
                    .setCategory(NotificationCompat.CATEGORY_PROGRESS)
                    .build()

                manager.notify(message.id, notification)
                delay(100L)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(id: String, name: String, importance: Int) {
        val channel = NotificationChannel(id, name, importance).apply {
            description = CHANNEL_DESCRIPTION
        }

        manager.createNotificationChannel(channel)
    }
}