package com.taetae98.room.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.taetae98.room.base.BaseDao

@Dao
interface ToDoDao : BaseDao<ToDo> {
    @Transaction
    @Query("SELECT * FROM ToDo WHERE drawerName = :drawerName")
    fun findLiveDataByDrawerName(drawerName: String): LiveData<MutableList<ToDo>>
}