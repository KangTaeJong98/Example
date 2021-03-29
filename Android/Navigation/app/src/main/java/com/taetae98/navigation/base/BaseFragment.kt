package com.taetae98.navigation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<VB: ViewDataBinding>(private val layoutId: Int) : Fragment() {
    protected val binding by lazy { DataBindingUtil.inflate<VB>(layoutInflater, layoutId, null, false) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        onCreateViewDataBinding()
        return binding.root
    }

    private fun onCreateViewDataBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
    }

    protected fun setSupportActionBar(toolbar: Toolbar) {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }
}