package com.taetae98.navigation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taetae98.navigation.R
import com.taetae98.navigation.base.BaseFragment
import com.taetae98.navigation.databinding.FragmentMessageBinding
import com.taetae98.navigation.utility.DataBinding

class MessageFragment : BaseFragment(), DataBinding<FragmentMessageBinding> {
    override val binding: FragmentMessageBinding by lazy { DataBinding.get(this, R.layout.fragment_message) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateViewDataBinding()
        return binding.root
    }

    private fun onCreateViewDataBinding() {
        binding.lifecycleOwner = this
    }
}