package com.taetae98.glidecustomtarget

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

object BindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "store"], requireAll = true)
    fun ImageView.imageUrl(imageUrl: String, store: Boolean) {
        Glide.with(this).asBitmap().load(
            GlideUrl(imageUrl) {
                mapOf(
                    "User-Agent" to "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Mobile Safari/537.36"
                )
            }
        ).into(
            object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    setImageBitmap(resource)
                    if (store) {
                        ImageManager.saveBitmap(context, imageUrl.substringAfterLast("/"), resource)
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            }
        )
    }
}