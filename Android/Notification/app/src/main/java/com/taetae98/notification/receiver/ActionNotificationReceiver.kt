package com.taetae98.notification.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.taetae98.notification.dto.Message
import com.taetae98.notification.manager.ActionNotificationManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ActionNotificationReceiver : BroadcastReceiver() {
    companion object {
        const val MESSAGE = "Message"
        const val ACTION_DELETE = "com.taetae98.notification.ACTION.DELETE"
    }

    @Inject
    lateinit var manager: ActionNotificationManager

    override fun onReceive(context: Context, intent: Intent) {
        when(intent.action) {
            ACTION_DELETE -> {
                val message = intent.getSerializableExtra(MESSAGE) as Message
                manager.cancel(message)
            }
        }
    }
}