package com.taetae98.bridge.fragment

import androidx.navigation.fragment.navArgs
import com.taetae98.bridge.R
import com.taetae98.bridge.databinding.BindingFragment
import com.taetae98.bridge.databinding.FragmentCodeBinding

class CodeFragment : BindingFragment<FragmentCodeBinding>(R.layout.fragment_code) {
    private val args by navArgs<CodeFragmentArgs>()
    private val code by lazy { args.code }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.code = code
    }
}