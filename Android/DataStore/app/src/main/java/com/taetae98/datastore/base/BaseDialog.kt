package com.taetae98.datastore.base

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

abstract class BaseDialog<VB: ViewDataBinding>(private val layoutId: Int) : DialogFragment() {
    protected val binding by lazy { DataBindingUtil.inflate<VB>(layoutInflater, layoutId, null, false) }

    protected fun setLayout(width: Int = ViewGroup.LayoutParams.WRAP_CONTENT, height: Int = ViewGroup.LayoutParams.WRAP_CONTENT) {
        dialog?.window?.setLayout(width, height)
    }

    protected fun setBackground(color: Int) {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(color))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateViewDataBinding()
        return binding.root
    }

    private fun onCreateViewDataBinding() {
        binding.lifecycleOwner = this
    }
}