package com.taetae98.navigation.fragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.taetae98.navigation.R
import com.taetae98.navigation.base.BaseFragment
import com.taetae98.navigation.databinding.FragmentPendingIntentBinding
import com.taetae98.navigation.utility.DataBinding

class PendingFragment : BaseFragment(), DataBinding<FragmentPendingIntentBinding> {
    override val binding: FragmentPendingIntentBinding by lazy { DataBinding.get(this, R.layout.fragment_pending_intent) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateOnPending()
        return binding.root
    }

    private fun onCreateOnPending() {
        binding.setOnPending {
            val manager = NotificationManagerCompat.from(requireContext())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                manager.createNotificationChannel(NotificationChannel("notification", "notification", NotificationManager.IMPORTANCE_HIGH))
            }

            val intent = NavDeepLinkBuilder(requireContext())
                .setGraph(R.navigation.activity_main_navigation)
                .setDestination(R.id.deepLinkFragment)
                .setArguments(Bundle().apply {
                    putString("arg", "Pending")
                })
                .createPendingIntent()

            manager.notify(1, NotificationCompat.Builder(requireContext(), "notification").apply {
                setSmallIcon(R.drawable.ic_notification)
                setContentTitle("Deep Link")
                setContentIntent(intent)
            }.build())
        }
    }
}