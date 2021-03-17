package com.taetae98.hilt.database

import androidx.lifecycle.LiveData
import com.taetae98.hilt.data.SummonerEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SummonerEntityRepository @Inject constructor(
    private val summonerEntityDao: SummonerEntityDao
) {
    fun findLiveData(): LiveData<List<SummonerEntity>> {
        return summonerEntityDao.findLiveData()
    }

    fun insertSummonerEntity(summonerEntity: SummonerEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            summonerEntityDao.insert(summonerEntity)
        }
    }

    fun deleteSummonerEntity(summonerEntity: SummonerEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            summonerEntityDao.delete(summonerEntity)
        }
    }

    fun updateSummonerEntity(summonerEntity: SummonerEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            summonerEntityDao.update(summonerEntity)
        }
    }
}