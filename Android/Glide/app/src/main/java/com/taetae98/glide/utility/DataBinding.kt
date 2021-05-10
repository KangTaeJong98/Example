package com.taetae98.glide.utility

import android.view.LayoutInflater
import android.view.ViewGroup
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

        fun<VB: ViewDataBinding> get(layout: ViewGroup, resId: Int): VB {
            return DataBindingUtil.inflate(LayoutInflater.from(layout.context), resId, layout, true)
        }
    }
}