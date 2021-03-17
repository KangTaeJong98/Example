package com.taetae98.hilt.data

import com.google.gson.annotations.SerializedName

data class Summoner(
    val id: String = "",
    val accountId: String = "",
    @SerializedName("puuid")
    val puuId: String = "",
    val name: String = "",
    val profileIconId: Int = 0,
    val revisionDate: Long = 0L,
    val summonerLevel: Int = 0,
)