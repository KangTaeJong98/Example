package com.taetae98.notification

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavDeepLinkBuilder

class BasicNotificationEditReceiver : BroadcastReceiver() {
    companion object {
        const val ACTION_EDIT = "action_edit"
    }

    override fun onReceive(context: Context, intent: Intent) {
        when(intent.action) {
            ACTION_EDIT -> {
                onActionEdit(context, intent)
            }
        }
    }

    private fun onActionEdit(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title")
        val message = intent.getStringExtra("message")

        val pending = NavDeepLinkBuilder(context)
                .setGraph(R.navigation.main_navigation)
                .setDestination(R.id.mainFragment)
                .setArguments(
                        Bundle().apply {
                            putString("title", title)
                            putString("message", message)
                        }
                )
                .createPendingIntent()

        val manager = context.getSystemService(AlarmManager::class.java)
        manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pending)
    }
}