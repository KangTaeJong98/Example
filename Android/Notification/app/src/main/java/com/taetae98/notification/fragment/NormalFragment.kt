package com.taetae98.notification.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taetae98.notification.R
import com.taetae98.notification.base.BindingFragment
import com.taetae98.notification.databinding.FragmentNormalBinding
import com.taetae98.notification.dto.Message
import com.taetae98.notification.manager.NormalNotificationManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NormalFragment : BindingFragment<FragmentNormalBinding>(R.layout.fragment_normal) {
    @Inject
    lateinit var manager: NormalNotificationManager

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
            val title = binding.titleInputLayout.editText!!.text.toString()
            val message = binding.messageInputLayout.editText!!.text.toString()

            manager.notify(Message(title = title, message = message))
            binding.titleInputLayout.editText?.setText("")
            binding.messageInputLayout.editText?.setText("")
        }
    }
}