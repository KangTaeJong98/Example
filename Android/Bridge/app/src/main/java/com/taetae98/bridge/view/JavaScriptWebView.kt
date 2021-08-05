package com.taetae98.bridge.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.taetae98.bridge.TAG

@SuppressLint("SetJavaScriptEnabled")
class JavaScriptWebView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : WebView(context, attrs, defStyleAttr, defStyleRes) {
    private var isLoading = true
    private var callback: ((html: String) -> Unit)? = null

    init {
        settings.javaScriptEnabled = true
        webChromeClient = WebChromeClient()
        webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                isLoading = true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                isLoading = false
            }
        }

        addJavascriptInterface(object {
            @JavascriptInterface
            fun getHTMLCode(html: String) {
                Log.d(TAG, "getHTMLCode : $html")
                callback?.invoke(html)
            }
        }, "Bridge")
    }

    fun postGetHTMLCode(callback:(html: String) -> Unit) {
        if (isLoading) {
            Toast.makeText(context, "Loading try later", Toast.LENGTH_SHORT).show()
            return
        }

        this.callback = callback
        loadUrl("javascript:window.Bridge.getHTMLCode(document.getElementsByTagName('html')[0].innerHTML);")
    }
}