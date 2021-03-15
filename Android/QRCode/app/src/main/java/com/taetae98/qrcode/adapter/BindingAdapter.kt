package com.taetae98.qrcode.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder

object BindingAdapter {
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