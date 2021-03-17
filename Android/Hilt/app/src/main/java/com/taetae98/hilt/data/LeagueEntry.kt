package com.taetae98.hilt.data

data class LeagueEntry(
    val queueType: String = "",
    val tier: String = "",
    val rank: String = "",
    val leaguePoints: Int = 0,
    val wins: Int = 0,
    val losses: Int = 0
)