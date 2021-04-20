package com.taetae98.navigation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.taetae98.navigation.R
import com.taetae98.navigation.base.BaseFragment
import com.taetae98.navigation.databinding.FragmentDeepLinkBinding
import com.taetae98.navigation.utility.DataBinding

class DeepLinkFragment : BaseFragment(), DataBinding<FragmentDeepLinkBinding> {
    override val binding: FragmentDeepLinkBinding by lazy { DataBinding.get(this, R.layout.fragment_deep_link) }

    private val args by navArgs<DeepLinkFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateViewDataBinding()
        onCreateArg()

        return binding.root
    }

    private fun onCreateViewDataBinding() {
        binding.lifecycleOwner = this
    }

    private fun onCreateArg() {
        binding.arg = args.arg
    }
}