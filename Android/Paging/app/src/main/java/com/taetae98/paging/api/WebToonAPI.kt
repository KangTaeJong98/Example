package com.taetae98.paging.api

import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface WebToonAPI {
    companion object {
        private const val BASE_URL = "https://comic.naver.com/webtoon/"

        @Volatile
        private var instance: WebToonAPI? = null

        fun getInstance(): WebToonAPI {
            return instance ?: synchronized(this) {
                instance ?: Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .build()
                        .create(WebToonAPI::class.java).also {
                    instance = it
                }
            }
        }
    }

    @GET("weekdayList.nhn")
    suspend fun weekdayWebToonList(@Query("week") week: String): ResponseBody

    @GET("list.nhn")
    suspend fun webToonEpisodeList(@Query("titleId") id: Long, @Query("page") page: Int = 1): ResponseBody

    @GET("detail.nhn")
    suspend fun viewer(@Query("titleId") id: Long, @Query("no") episode: Int): ResponseBody
}