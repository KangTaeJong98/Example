package com.taetae98.widget.util

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseMethod

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("colorDrawable")
    fun ImageView.colorDrawable(@ColorInt color: Int) {
        setImageDrawable(ColorDrawable(color))
    }

    @JvmStatic
    @InverseMethod("intToString")
    fun stringToInt(value: String): Int {
        return value.toInt()
    }

    @JvmStatic
    @InverseMethod("stringToInt")
    fun intToString(value: Int): String {
        return value.toString()
    }

    @JvmStatic
    @BindingAdapter("colorString")
    fun TextView.colorString(@ColorInt color: Int) {
        val alpha = Color.alpha(color).toLong()
        val red = Color.red(color).toLong()
        val green = Color.green(color).toLong()
        val blue = Color.blue(color).toLong()

        "#${java.lang.String.format("%08x", blue + green*256L + red*256L*256L + alpha*256L*256L*256L)}".also {
            text = it
        }
    }
}