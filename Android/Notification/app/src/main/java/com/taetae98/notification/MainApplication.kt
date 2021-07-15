package com.taetae98.notification

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.taetae98.notification.dto.Message
import com.taetae98.notification.manager.CustomNotificationManager
import com.taetae98.notification.manager.GroupNotificationManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {
    @Inject
    lateinit var manager: CustomNotificationManager

    override fun onCreate() {
        super.onCreate()
        manager.notify(Message())
    }
}