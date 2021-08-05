package com.taetae98.flow.databinding

import android.app.Activity
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

interface DataBinding<VB: ViewDataBinding> {
    val binding: VB

    companion object {
        fun<VB: ViewDataBinding> get(activity: Activity, @LayoutRes layout: Int): VB {
            return DataBindingUtil.setContentView(activity, layout)
        }
    }



}