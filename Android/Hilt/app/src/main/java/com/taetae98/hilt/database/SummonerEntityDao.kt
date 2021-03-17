package com.taetae98.hilt.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.taetae98.hilt.base.BaseDao
import com.taetae98.hilt.data.SummonerEntity

@Dao
interface SummonerEntityDao : BaseDao<SummonerEntity> {
    @Query("SELECT * FROM SummonerEntity")
    fun findLiveData(): LiveData<List<SummonerEntity>>
}