package com.taetae98.widget.api

import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

object ApiBuilder {
    inline fun<reified T> build(url: String, factory: Iterable<Converter.Factory>): T {
        val client = OkHttpClient.Builder()
//            .connectTimeout(Int.MAX_VALUE.toLong(), TimeUnit.MILLISECONDS)
//            .readTimeout(Int.MAX_VALUE.toLong(), TimeUnit.MILLISECONDS)
//            .writeTimeout(Int.MAX_VALUE.toLong(), TimeUnit.MILLISECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(url)
            .client(client)
            .apply {
                for (factory in factory) {
                    addConverterFactory(factory)
                }
            }
            .build()
            .create(T::class.java)
    }
}