package com.taetae98.http.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.taetae98.http.MainActivity

abstract class BaseFragment<VB: ViewDataBinding>(private val layoutId: Int) : Fragment() {
    protected lateinit var binding: VB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)

        init()
        return binding.root
    }

    protected open fun init() {

    }

    protected fun setSupportActionBar(toolbar: Toolbar) {
        if (activity is MainActivity) {
            (activity as MainActivity).initSupportActionBar(toolbar)
        }
    }
}