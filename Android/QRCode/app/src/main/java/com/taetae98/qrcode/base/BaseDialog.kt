package com.taetae98.qrcode.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

abstract class BaseDialog<VB: ViewDataBinding>(private val layoutId: Int) : DialogFragment() {
    protected lateinit var binding: VB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)

        init()
        return binding.root
    }

    fun setLayout(width: Int = ViewGroup.LayoutParams.WRAP_CONTENT, height: Int = ViewGroup.LayoutParams.WRAP_CONTENT) {
        dialog?.window?.setLayout(width, height)
    }

    protected open fun init() {

    }

    private fun initDataBinding() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, null, false)
    }
}