package com.taetae98.paging.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.target.Target

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageUrl(view: ImageView, url: String?) {
        if (url == null || url.isBlank()) {
            return
        }

        Glide.with(view).load(GlideUrl(url) {
            mapOf(
                    "User-Agent" to "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Mobile Safari/537.36"
            )
        }).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(view)
    }
}