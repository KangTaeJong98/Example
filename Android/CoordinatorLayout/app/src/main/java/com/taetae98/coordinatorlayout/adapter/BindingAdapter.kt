package com.taetae98.coordinatorlayout.adapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "error"], requireAll = false)
    fun loadImage(view: ImageView, url: String?, error: Drawable?) {
        Glide.with(view).load(url).error(error).into(view)
    }
}