package com.taetae98.datastore.api

import com.taetae98.datastore.dto.InformationBody
import com.taetae98.datastore.dto.LoginBody
import com.taetae98.datastore.dto.SmartGachonMessage
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface SmartGachonAPI {
    companion object {
        private const val BASE_URL = "http://smart.gachon.ac.kr:8080/"

        private var instance: SmartGachonAPI? = null

        fun getInstance(): SmartGachonAPI {
            return instance ?: synchronized(this) {
                instance ?: Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(SmartGachonAPI::class.java).also {
                        instance = it
                    }
            }
        }
    }

    @POST("WebJSON")
    fun login(
        @Body
        body: LoginBody
    ): Call<SmartGachonMessage>

    @POST("WebJSON")
    fun information(
        @Body
        body: InformationBody
    ): Call<SmartGachonMessage>
}