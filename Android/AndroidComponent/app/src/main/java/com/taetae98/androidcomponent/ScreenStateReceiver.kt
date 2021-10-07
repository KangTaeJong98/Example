package com.taetae98.androidcomponent

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ScreenStateReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when(intent.action) {
            Intent.ACTION_SCREEN_ON -> {
                onScreenOn(context)
            }
        }
    }

    private fun onScreenOn(context: Context) {
        Toast.makeText(context, "Screen On", Toast.LENGTH_SHORT).show()
    }
}