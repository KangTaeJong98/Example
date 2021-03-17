package com.taetae98.hilt.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taetae98.hilt.api.RiotLeagueAPI
import com.taetae98.hilt.api.RiotSpectatorAPI
import com.taetae98.hilt.data.SummonerInformation
import com.taetae98.hilt.database.SummonerEntityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SummonerEntityViewModel @Inject constructor(
    summonerEntityRepository: SummonerEntityRepository,
    riotSpectatorAPI: RiotSpectatorAPI,
    riotLeagueAPI: RiotLeagueAPI
) : ViewModel() {
    private val hasData = mutableSetOf<String>()
    val summonerInformationLiveData = MutableLiveData<List<SummonerInformation>>(emptyList())

    private val summonerEntityLiveData = summonerEntityRepository.findLiveData().apply {
        observeForever {
            CoroutineScope(Dispatchers.IO).launch {
                val list = ArrayList<SummonerInformation>()
                it.forEach { entity ->
                    try {
                        val summoner = riotSpectatorAPI.getSummoner(entity.name)
                        val leagueEntry = riotLeagueAPI.getLeagueEntry(summoner.id)

                        list.add(SummonerInformation(summoner, entity, leagueEntry).also { information ->
                            Log.d("PASS", information.toString())
                        })
                    } catch (e: Exception) {
                        Log.d("PASS", e.toString())
                        summonerEntityRepository.deleteSummonerEntity(entity)
                    }
                }

                withContext(Dispatchers.Main) {
                    summonerInformationLiveData.value = list
                }
            }
        }
    }
}