package com.taetae98.notification.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taetae98.notification.R
import com.taetae98.notification.base.BindingFragment
import com.taetae98.notification.databinding.FragmentGroupBinding
import com.taetae98.notification.dto.Message
import com.taetae98.notification.manager.GroupNotificationManager
import com.taetae98.notification.manager.NormalNotificationManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GroupFragment : BindingFragment<FragmentGroupBinding>(R.layout.fragment_group) {
    @Inject
    lateinit var manager: GroupNotificationManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateSupportActionBar()
        onCreateOnNotify()

        return binding.root
    }

    private fun onCreateSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun onCreateOnNotify() {
        binding.setOnNotify {
            val title1 = binding.titleInputLayout1.editText!!.text.toString()
            val message1 = binding.messageInputLayout1.editText!!.text.toString()

            val title2 = binding.titleInputLayout2.editText!!.text.toString()
            val message2 = binding.messageInputLayout2.editText!!.text.toString()

            manager.notify(
                listOf(
                    Message(title = title1, message = message1),
                    Message(title = title2, message = message2),
                )
            )
        }
    }
}