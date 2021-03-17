package com.taetae98.hilt.api

import com.taetae98.hilt.data.LeagueEntry
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface RiotLeagueAPI {
    companion object {
        private const val BASE_URL = "https://kr.api.riotgames.com/lol/league/v4/"

        fun create(): RiotLeagueAPI {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RiotLeagueAPI::class.java)
        }
    }

    @Headers(
        "X-Riot-Token: RGAPI-0e812efc-94a7-41ea-af56-7a4be71e789d"
    )
    @GET("entries/by-summoner/{encryptedSummonerId}")
    suspend fun getLeagueEntry(
        @Path("encryptedSummonerId") encryptedSummonerId: String
    ): List<LeagueEntry>
}