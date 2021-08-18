package com.taetae98.googleapi.databinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.taetae98.googleapi.base.BaseDialog

abstract class BindingDialog<VB: ViewDataBinding>(
    @LayoutRes
    protected val layoutId: Int
) : BaseDialog(), DataBinding<VB> {
    override val binding: VB by lazy { DataBinding.get(this, layoutId) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        onCreateViewDataBinding()

        return binding.root
    }

    protected open fun onCreateViewDataBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
    }
}