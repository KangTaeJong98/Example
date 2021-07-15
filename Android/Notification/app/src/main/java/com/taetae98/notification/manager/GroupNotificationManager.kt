package com.taetae98.notification.manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.taetae98.notification.R
import com.taetae98.notification.dto.Message
import com.taetae98.notification.receiver.ActionNotificationReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GroupNotificationManager @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    companion object {
        private const val CHANNEL_ID = "com.taetae98.notification.GROUP"
        private const val CHANNEL_NAME = "Group"
        private const val CHANNEL_DESCRIPTION = "This is Group Notification"
        private const val GROUP_KEY = "com.taetae98.notification.GROUP.KEY"
    }

    private val manager by lazy {
        NotificationManagerCompat.from(context)
    }

    fun notify(list: List<Message>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        for (message in list) {
            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_android)
                .setContentTitle(message.title)
                .setContentText(message.message)
                .setGroup(GROUP_KEY)
                .build()

            manager.notify(message.id, notification)
        }

        val summaryNotification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(context.getString(R.string.n_message, list.size))
            .setContentText(context.getString(R.string.tab_to_open))
            .setSubText(context.getString(R.string.group))
            .setSmallIcon(R.drawable.ic_android)
            .setGroup(GROUP_KEY)
            .setGroupSummary(true)
            .build()

        manager.notify(1234, summaryNotification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH).apply {
            description = CHANNEL_DESCRIPTION
        }

        manager.createNotificationChannel(channel)
    }
}