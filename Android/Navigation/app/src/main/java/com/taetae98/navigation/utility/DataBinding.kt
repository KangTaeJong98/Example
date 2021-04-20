package com.taetae98.navigation.utility

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

interface DataBinding<VB: ViewDataBinding> {
    val binding: VB

    companion object {
        fun<VB: ViewDataBinding> get(activity: AppCompatActivity, resId: Int): VB {
            return DataBindingUtil.setContentView(activity, resId)
        }

        fun<VB: ViewDataBinding> get(fragment: Fragment, resId: Int): VB {
            return DataBindingUtil.inflate(fragment.layoutInflater, resId, null, false)
        }
    }
}