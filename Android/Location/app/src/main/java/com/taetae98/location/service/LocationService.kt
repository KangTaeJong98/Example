package com.taetae98.location.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.taetae98.location.R
import com.taetae98.location.singleton.GPSManager

class LocationService : Service() {
    companion object {
        const val NOTIFICATION_FOREGROUND_ID = 1000
        const val NOTIFICATION_CHANNEL_ID = "Location"
        const val NOTIFICATION_CHANNEL_NAME = "Location"
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        startForeground(NOTIFICATION_FOREGROUND_ID, NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID).build())

        if (checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            with(getSystemService(Context.LOCATION_SERVICE) as LocationManager) {
                val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000L, 5F) {
                    manager.notify(NOTIFICATION_FOREGROUND_ID, buildNotification(it))
                }

                requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000L, 5F) {
                    manager.notify(NOTIFICATION_FOREGROUND_ID, buildNotification(it))
                }
            }
        }


        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_MIN)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.createNotificationChannel(channel)
    }

    private fun buildNotification(location: Location): Notification {
        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_location)
            setContentTitle(GPSManager.getInstance(this@LocationService).getAddress(location))
            setContentText(
                StringBuilder()
                    .append("위도").append(" : ").append(location.latitude).appendLine()
                    .append("경도").append(" : ").append(location.longitude).toString()
            )
            setStyle(NotificationCompat.BigTextStyle())
        }.build()
    }
}