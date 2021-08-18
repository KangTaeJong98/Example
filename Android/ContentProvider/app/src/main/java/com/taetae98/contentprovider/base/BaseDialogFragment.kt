package com.taetae98.contentprovider.base

import android.graphics.drawable.Drawable
import androidx.annotation.AnimRes

interface BaseDialogFragment {
    fun setLayout(width: Int, height: Int)
    fun setBackground(drawable: Drawable?)
    fun setAnimation(@AnimRes resId: Int)
    fun finish()
}