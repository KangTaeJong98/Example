package com.taetae98.room.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.taetae98.room.base.BaseDao

@Dao
interface DrawerDao : BaseDao<Drawer> {
    @Query("SELECT * FROM Drawer")
    fun findLiveData(): LiveData<MutableList<Drawer>>

    @Query("SELECT * FROM Drawer")
    fun findLiveDataWithToDo(): LiveData<MutableList<DrawerWithToDo>>
}