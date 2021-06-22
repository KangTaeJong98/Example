package com.taetae98.notification.fragment

import androidx.navigation.fragment.navArgs
import com.taetae98.notification.R
import com.taetae98.notification.base.BindingFragment
import com.taetae98.notification.databinding.FragmentReceiveBinding

class ReceiveFragment : BindingFragment<FragmentReceiveBinding>(R.layout.fragment_receive) {
    private val args by navArgs<ReceiveFragmentArgs>()
    private val message by lazy { args.message }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.message = message
    }
}