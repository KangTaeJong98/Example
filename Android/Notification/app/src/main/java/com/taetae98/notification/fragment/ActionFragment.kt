package com.taetae98.notification.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.taetae98.notification.R
import com.taetae98.notification.base.BindingFragment
import com.taetae98.notification.databinding.FragmentActionBinding
import com.taetae98.notification.dto.Message
import com.taetae98.notification.manager.ActionNotificationManager
import com.taetae98.notification.manager.NormalNotificationManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ActionFragment : BindingFragment<FragmentActionBinding>(R.layout.fragment_action) {
    @Inject
    lateinit var manager: ActionNotificationManager

    private val args by navArgs<ActionFragmentArgs>()
    private val message by lazy { args.message }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateSupportActionBar()
        onCreateOnNotify()

        return binding.root
    }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.message = message
    }

    private fun onCreateSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun onCreateOnNotify() {
        binding.setOnNotify {
            val title = binding.titleInputLayout.editText!!.text.toString()
            val message = binding.messageInputLayout.editText!!.text.toString()

            manager.notify(Message(title = title, message = message))
            binding.titleInputLayout.editText?.setText("")
            binding.messageInputLayout.editText?.setText("")
        }
    }
}