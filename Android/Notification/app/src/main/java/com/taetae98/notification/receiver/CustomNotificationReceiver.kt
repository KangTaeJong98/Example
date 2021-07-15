package com.taetae98.notification.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast

class CustomNotificationReceiver : BroadcastReceiver() {
    companion object {
        const val ACTION_LOCK = "ACTION_LOCK"
        const val ACTION_LOCK_OPEN = "ACTION_LOCK_OPEN"
        const val ACTION_ROTATION = "ACTION_ROTATION"
        const val ACTION_PORTRAIT = "ACTION_PORTRAIT"
        const val ACTION_LANDSCAPE = "ACTION_LANDSCAPE"
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (!Settings.System.canWrite(context)) {
            requestPermission(context)
            return
        }

        Toast.makeText(context, "onReceive : ${intent.action}", Toast.LENGTH_SHORT).show()
        when (intent.action) {
            ACTION_LOCK -> {
                Settings.System.putInt(context.contentResolver, Settings.System.ACCELEROMETER_ROTATION, 0)
            }
            ACTION_LOCK_OPEN -> {
                Settings.System.putInt(context.contentResolver, Settings.System.ACCELEROMETER_ROTATION, 1)
            }
            ACTION_PORTRAIT -> {
                Settings.System.putInt(context.contentResolver, Settings.System.USER_ROTATION, 0)
            }
            ACTION_LANDSCAPE -> {
                Settings.System.putInt(context.contentResolver, Settings.System.USER_ROTATION, 1)
            }
            ACTION_ROTATION -> {
                val value = Settings.System.getInt(context.contentResolver, Settings.System.USER_ROTATION, 0)
                Settings.System.putInt(context.contentResolver, Settings.System.USER_ROTATION, (value + 1) % 4)
            }
        }
    }

    private fun requestPermission(context: Context) {
        Intent(
            Settings.ACTION_MANAGE_WRITE_SETTINGS,
            Uri.parse("package:${context.packageName}")
        ).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }.let {
            context.startActivity(it)
        }
    }
}