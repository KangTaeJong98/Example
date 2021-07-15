package com.taetae98.notification.utility

import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

interface DataBinding<VB: ViewDataBinding> {
    val binding: VB

    companion object {
        fun<VB: ViewDataBinding> get(activity: AppCompatActivity, @LayoutRes resId: Int): VB {
            return DataBindingUtil.setContentView(activity, resId)
        }

        fun<VB: ViewDataBinding> get(fragment: Fragment, @LayoutRes resId: Int): VB {
            return DataBindingUtil.inflate(fragment.layoutInflater, resId, null, false)
        }

        fun<VB: ViewDataBinding> get(layout: ViewGroup, @LayoutRes resId: Int): VB {
            return DataBindingUtil.inflate(LayoutInflater.from(layout.context), resId, layout, true)
        }

        fun<VB: ViewDataBinding> get(dialog: Dialog, @LayoutRes resId: Int): VB {
            return DataBindingUtil.inflate(dialog.layoutInflater, resId, null, false)
        }
    }
}