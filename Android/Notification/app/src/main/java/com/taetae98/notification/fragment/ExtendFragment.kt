package com.taetae98.notification.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.taetae98.notification.R
import com.taetae98.notification.base.BindingFragment
import com.taetae98.notification.databinding.FragmentExtendBinding
import com.taetae98.notification.dto.Message
import com.taetae98.notification.manager.ExtendNotificationManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExtendFragment : BindingFragment<FragmentExtendBinding>(R.layout.fragment_extend) {
    @Inject
    lateinit var manager: ExtendNotificationManager

    private val onSelectImageActivityResult = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        manager.imageNotify(
            Message(
                title = binding.titleInputLayout.editText!!.text.toString(),
                message = binding.messageInputLayout.editText!!.text.toString()
            ),
            BitmapFactory.decodeFileDescriptor(
                requireContext().contentResolver.openFileDescriptor(it, "r")?.fileDescriptor
            )
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateSupportActionBar()
        onCreateOnImageNotify()
        onCreateOnProgressNotify()

        return binding.root
    }

    private fun onCreateSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun onCreateOnImageNotify() {
        binding.setOnImageNotify {
            onSelectImageActivityResult.launch("image/*")
        }
    }

    private fun onCreateOnProgressNotify() {
        binding.setOnProgressNotify {
            manager.progressNotify(
                Message(
                    title = binding.titleInputLayout.editText!!.text.toString(),
                    message = binding.messageInputLayout.editText!!.text.toString()
                )
            )
        }
    }
}