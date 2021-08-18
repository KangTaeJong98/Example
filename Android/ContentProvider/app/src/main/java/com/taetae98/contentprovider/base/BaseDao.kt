package com.taetae98.contentprovider.base

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface BaseDao<T: Any> {
    @Insert
    fun insert(data: T): Long

    @Insert
    fun insert(vararg data: T): LongArray

    @Insert
    fun insert(data: List<T>): LongArray
}