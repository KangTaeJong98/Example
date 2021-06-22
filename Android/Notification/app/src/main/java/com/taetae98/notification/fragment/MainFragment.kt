package com.taetae98.notification.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.taetae98.notification.R
import com.taetae98.notification.base.BindingFragment
import com.taetae98.notification.databinding.FragmentMainBinding
import com.taetae98.notification.notification.BasicNotificationManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BindingFragment<FragmentMainBinding>(R.layout.fragment_main) {
    private val args by navArgs<MainFragmentArgs>()
    private val title by lazy { args.title }
    private val message by lazy { args.message }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateSupportActionBar()
        onCreateOnSubmit()

        return binding.root
    }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.title = title
        binding.message = message
    }

    private fun onCreateSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun onCreateOnSubmit() {
        with(binding) {
            setOnSubmit {
                BasicNotificationManager(requireContext()).notify(
                    titleInputLayout.editText!!.text.toString(),
                    messageInputLayout.editText!!.text.toString()
                )
            }
        }
    }
}