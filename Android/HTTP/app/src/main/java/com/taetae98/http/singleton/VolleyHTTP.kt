package com.taetae98.http.singleton

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.Volley

class VolleyHTTP private constructor(context: Context) {
    companion object {
        private var instance: VolleyHTTP? = null

        fun getInstance(context: Context): VolleyHTTP {
            return instance ?: synchronized(this) {
                instance ?: VolleyHTTP(context).also {
                    instance = it
                }
            }
        }
    }

    private val queue by lazy { Volley.newRequestQueue(context) }

    fun<T> request(req: Request<T>) {
        queue.add(req)
    }
}