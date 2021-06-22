package com.taetae98.notification.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.taetae98.notification.utility.DataBinding

abstract class BindingBottomSheetDialog<VB: ViewDataBinding>(
    @LayoutRes
    protected val layoutId: Int
) : BaseBottomSheetDialog(), DataBinding<VB> {
    override val binding: VB by lazy { DataBinding.get(this, layoutId) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        onCreateViewDataBinding()
        return binding.root
    }

    protected open fun onCreateViewDataBinding() {
        binding.lifecycleOwner = this
    }
}