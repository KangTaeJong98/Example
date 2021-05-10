package com.taetae98.glide.adapter

import android.app.ActionBar
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.target.Target

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("imageUri")
    fun setImageUri(view: ImageView, uri: String?) {
        if (uri == null || uri.trim().isBlank()) {
            return
        }

        Log.d("PASS", "Load : $uri")
        Glide.with(view).load(
            GlideUrl(uri) {
                mapOf(
                    "User-Agent" to "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Mobile Safari/537.36"
                )
            }
                    .updateDiskCacheKey(DiskCacheStrategy.)
        ).override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL).into(view)
    }
}