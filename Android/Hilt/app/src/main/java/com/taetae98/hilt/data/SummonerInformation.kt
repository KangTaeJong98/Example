package com.taetae98.hilt.data

data class SummonerInformation(
    val summoner: Summoner = Summoner(),
    val entity: SummonerEntity = SummonerEntity(),
    val leagueEntry: List<LeagueEntry> = emptyList()
)