package com.taetae98.navigation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taetae98.navigation.R
import com.taetae98.navigation.base.BaseFragment
import com.taetae98.navigation.databinding.FragmentPhoneBookBinding
import com.taetae98.navigation.utility.DataBinding

class PhoneBookFragment : BaseFragment(), DataBinding<FragmentPhoneBookBinding> {
    override val binding: FragmentPhoneBookBinding by lazy { DataBinding.get(this, R.layout.fragment_phone_book) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateViewDataBinding()
        return binding.root
    }

    private fun onCreateViewDataBinding() {
        binding.lifecycleOwner = this
    }
}