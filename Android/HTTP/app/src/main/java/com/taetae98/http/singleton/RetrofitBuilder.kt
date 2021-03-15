package com.taetae98.http.singleton

import com.taetae98.http.dto.RequestResult
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

object RetrofitBuilder {
    val retrofit: RetrofitHTTP = Retrofit.Builder()
        .baseUrl("${Server.PROTOCOL}://${Server.IP}:${Server.PORT}")
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RetrofitHTTP::class.java)

    interface RetrofitHTTP {
        @Headers(
            "Content-Type: application/x-www-form-urlencoded"
        )
        @GET("/application/x-www-form-urlencoded")
        fun getUrlencoded(@Query("parameter1") parameter1: Any? = null, @Query("parameter2") parameter2: Any? = null): Call<RequestResult>

        @Headers(
            "Content-Type: application/x-www-form-urlencoded"
        )
        @POST("/application/x-www-form-urlencoded")
        fun postUrlencoded(@QueryMap parameters: Map<String, String>? = null): Call<RequestResult>

        @Headers(
            "Content-Type: application/json"
        )
        @GET("/application/json")
        fun getJson(@Query("json") json: Map<String, String>? = null): Call<RequestResult>

        @Headers(
            "Content-Type: application/json"
        )
        @POST("/application/json")
        fun postJson(@Body parameters: Map<String, String>? = null): Call<RequestResult>
    }
}