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
import androidx.navigation.NavDeepLinkBuilder
import com.taetae98.notification.R
import com.taetae98.notification.dto.Message
import com.taetae98.notification.receiver.ActionNotificationReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import java.nio.file.Files.delete
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActionNotificationManager @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    companion object {
        private const val CHANNEL_ID = "com.taetae98.notification.ACTION"
        private const val CHANNEL_NAME = "Action"
        private const val CHANNEL_DESCRIPTION = "This is Action Notification"
    }

    private val manager by lazy { context.getSystemService(NotificationManager::class.java) }

    fun notify(message: Message) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        val pendingIntent = createPendingIntent(message)
        val deleteIntent = createDeleteAction(message)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_android)
            .setContentTitle(message.title)
            .setContentText(message.message)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(false) // true면 Notification 클릭시 삭제
            .setOngoing(true) // true면 직접 Notification을 지울 수 없음
            .setShowWhen(false) // false면 Notification 시간을 보이지 않음
            .setContentIntent(pendingIntent) // 알람 클릭시 Intent
            .addAction(R.drawable.ic_delete, context.getString(R.string.delete), deleteIntent)
            .build()

        manager.notify(message.id, notification)
    }

    fun cancel(message: Message) {
        manager.cancel(message.id)
    }

    private fun createPendingIntent(message: Message): PendingIntent {
        return NavDeepLinkBuilder(context)
            .setGraph(R.navigation.navigation_main)
            .setDestination(R.id.actionFragment)
            .setArguments(
                Bundle().apply {
                    putSerializable("message", message)
                }
            )
            .createPendingIntent()
    }

    private fun createDeleteAction(message: Message): PendingIntent {
        val intent = Intent(context, ActionNotificationReceiver::class.java).apply {
            action = ActionNotificationReceiver.ACTION_DELETE
            putExtra(ActionNotificationReceiver.MESSAGE, message)
            putExtra("id", message.id)
        }

        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH).apply {
            description = CHANNEL_DESCRIPTION
        }

        manager.createNotificationChannel(channel)
    }
}