package com.taetae98.room.adapter

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter

object DataBindingAdapter {
    @JvmStatic
    @BindingAdapter("bind:strike_thru")
    fun setStrikeThru(view: TextView, boolean: Boolean) {
        if (boolean) {
            view.paintFlags = view.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            view.paintFlags = view.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }
}