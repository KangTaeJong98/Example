package com.taetae98.contentprovider.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.taetae98.contentprovider.dto.SimpleData
import com.taetae98.contentprovider.base.BaseDao

@Dao
interface SimpleDataDao : BaseDao<SimpleData> {
    @Query("SELECT * FROM SimpleData")
    fun findAll(): List<SimpleData>

    @Query("SELECT * FROM SimpleData")
    fun findAllLiveData(): LiveData<List<SimpleData>>
}