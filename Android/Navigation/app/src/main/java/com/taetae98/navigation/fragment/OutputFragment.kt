package com.taetae98.navigation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.taetae98.navigation.R
import com.taetae98.navigation.base.BaseFragment
import com.taetae98.navigation.databinding.FragmentOutBinding
import com.taetae98.navigation.utility.DataBinding

class OutputFragment : BaseFragment(), DataBinding<FragmentOutBinding> {
    override val binding: FragmentOutBinding by lazy { DataBinding.get(this, R.layout.fragment_out) }

    private val args by navArgs<OutputFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateViewDataBinding()
        onCreateArgs()

        return binding.root
    }

    private fun onCreateViewDataBinding() {
        binding.lifecycleOwner = this
    }

    private fun onCreateArgs() {
        binding.args = args.args
    }
}