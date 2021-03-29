package com.taetae98.datastore.adapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("gif")
    fun setGif(view: ImageView, drawable: Drawable?) {
        Glide.with(view).load(drawable).into(view)
    }

    @JvmStatic
    @BindingAdapter("qrCode")
    fun setQRCode(view: ImageView, qrCode: String?) {
        if (qrCode == null) {
            view.setImageBitmap(null)
            return
        }

        val barcodeEncoder = BarcodeEncoder()
        val bitmap = barcodeEncoder.encodeBitmap(qrCode.toByteArray().toString(Charsets.ISO_8859_1), BarcodeFormat.QR_CODE, 400, 400)

        view.setImageBitmap(bitmap)
    }
}