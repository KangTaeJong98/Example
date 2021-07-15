package com.taetae98.notification.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.taetae98.notification.utility.DataBinding

abstract class BaseBindingActivity<VB: ViewDataBinding>(
    @LayoutRes
    protected val layoutId: Int
) : BaseActivity(), DataBinding<VB> {
    override val binding: VB by lazy { DataBinding.get(this, layoutId) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateViewDataBinding()
    }

    protected open fun onCreateViewDataBinding() {
        binding.lifecycleOwner = this
    }
}