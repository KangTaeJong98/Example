package com.taetae98.bridge.databinding

import android.webkit.WebView
import androidx.databinding.BindingAdapter

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("loadURL")
    fun loadURL(webView: WebView, url: String) {
        webView.loadUrl(url)
    }
}