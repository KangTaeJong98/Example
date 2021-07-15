package com.taetae98.notification.manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.taetae98.notification.R
import com.taetae98.notification.dto.Message
import com.taetae98.notification.receiver.CustomNotificationReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CustomNotificationManager @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    companion object {
        private const val CHANNEL_ID = "com.taetae98.notification.CUSTOM"
        private const val CHANNEL_NAME = "Custom"
        private const val CHANNEL_DESCRIPTION = "This is Custom Notification"
    }

    private val manager by lazy {
        NotificationManagerCompat.from(context)
    }

    fun notify(message: Message) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_android)
            .setShowWhen(false)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(createCustomContentView())
            .build()

        manager.notify(message.id, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH).apply {
            description = CHANNEL_DESCRIPTION
        }

        manager.createNotificationChannel(channel)
    }

    private fun createCustomContentView(): RemoteViews {
        return RemoteViews(context.packageName, R.layout.notification_content_view).apply {
            setOnClickPendingIntent(
                R.id.lock,
                PendingIntent.getBroadcast(
                    context,
                    1000,
                    Intent(context, CustomNotificationReceiver::class.java)
                        .apply {
                            action = CustomNotificationReceiver.ACTION_LOCK
                        }
                    , 0
                )
            )
            setOnClickPendingIntent(
                R.id.lock_open,
                PendingIntent.getBroadcast(
                    context,
                    1000,
                    Intent(context, CustomNotificationReceiver::class.java)
                        .apply {
                            action = CustomNotificationReceiver.ACTION_LOCK_OPEN
                        }
                    , 0
                )
            )
            setOnClickPendingIntent(
                R.id.rotation,
                PendingIntent.getBroadcast(
                    context,
                    1000,
                    Intent(context, CustomNotificationReceiver::class.java)
                        .apply {
                            action = CustomNotificationReceiver.ACTION_ROTATION
                        }
                    , 0
                )
            )
            setOnClickPendingIntent(
                R.id.portrait,
                PendingIntent.getBroadcast(
                    context,
                    1000,
                    Intent(context, CustomNotificationReceiver::class.java)
                        .apply {
                            action = CustomNotificationReceiver.ACTION_PORTRAIT
                        }
                    , 0
                )
            )
            setOnClickPendingIntent(
                R.id.landscape,
                PendingIntent.getBroadcast(
                    context,
                    1000,
                    Intent(context, CustomNotificationReceiver::class.java)
                        .apply {
                            action = CustomNotificationReceiver.ACTION_LANDSCAPE
                        }
                    , 0
                )
            )
        }
    }
}