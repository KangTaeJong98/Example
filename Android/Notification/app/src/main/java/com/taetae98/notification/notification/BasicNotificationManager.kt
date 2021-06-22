package com.taetae98.notification.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.taetae98.notification.BasicNotificationEditReceiver
import com.taetae98.notification.R

class BasicNotificationManager(private val context: Context) {
    companion object {
        private const val CHANNEL_ID = "BASIC"
        private const val CHANNEL_NAME = "Basic"
        private const val CHANNEL_DESCRIPTION = "Basic Channel"

        private var id = 0
    }

    fun notify(title: String, message: String) {
        createNotificationChannel()

        val pending = NavDeepLinkBuilder(context)
                .setGraph(R.navigation.main_navigation)
                .setDestination(R.id.receiveFragment)
                .setArguments(
                        Bundle().apply {
                            putString("title", title)
                            putString("message", message)
                        }
                )
                .createPendingIntent()

        val editPending = PendingIntent.getBroadcast(context, 0,
                Intent(context, BasicNotificationEditReceiver::class.java)
                        .setAction(BasicNotificationEditReceiver.ACTION_EDIT)
                        .putExtra("title", title)
                        .putExtra("message", message)
                        .putExtra("id", id), 0
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_android)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pending)
            .setAutoCancel(true)
            .addAction(R.drawable.ic_edit, "Edit", editPending)
            .build()

        context.getSystemService(NotificationManager::class.java).notify(id++, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.getSystemService(NotificationManager::class.java).createNotificationChannel(
                NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH).apply {
                    description = CHANNEL_DESCRIPTION
                    setShowBadge(true)
                }
            )
        }
    }
}