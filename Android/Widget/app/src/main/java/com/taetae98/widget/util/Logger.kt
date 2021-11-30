package com.taetae98.widget.util

import android.content.Intent
import android.util.Log
import retrofit2.Response

object Logger {
    private const val TAG = "CovidWidget"

    @JvmStatic
    @JvmOverloads
    fun d(message: Any?, throwable: Throwable? = null) {
        Log.d(TAG, message?.toString(), throwable)
    }

    @JvmStatic
    @JvmOverloads
    fun w(message: Any?, throwable: Throwable? = null) {
        Log.w(TAG, message?.toString(), throwable)
    }

    @JvmStatic
    @JvmOverloads
    fun e(message: Any?, throwable: Throwable? = null) {
        Log.e(TAG, message?.toString(), throwable)
    }

    @JvmStatic
    fun intent(message: Any?, intent: Intent?) {
        d("$message : $intent")
        d("Extra ${intent?.extras}")
        intent?.extras?.keySet()?.forEach { key ->
            d("Extra($key) : ${intent.extras?.get(key)}")
        }
    }

    @JvmStatic
    fun<T: Any> response(message: Any?, response: Response<T>) {
        d("$message : $response")
        if (response.isSuccessful) {
            d("$message : ${response.body()}")
        } else {
            w("$message : ${response.errorBody()?.string()}")
        }
    }
}