package com.taetae98.lifecycle.fragment

import com.taetae98.lifecycle.R
import com.taetae98.lifecycle.base.BaseFragment
import com.taetae98.lifecycle.databinding.FragmentCoroutineNextBinding

class CoroutineNextFragment : BaseFragment<FragmentCoroutineNextBinding>(R.layout.fragment_coroutine_next) {
    override fun init() {
        initSupportActionbar()
    }

    private fun initSupportActionbar() {
        setSupportActionBar(binding.toolbar)
    }
}