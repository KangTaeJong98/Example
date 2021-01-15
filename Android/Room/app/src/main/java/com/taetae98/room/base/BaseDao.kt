package com.taetae98.room.base

import androidx.room.*

@Dao
interface BaseDao<E: Any> {
    @Transaction
    @Insert
    suspend fun insert(vararg elements: E)

    @Transaction
    @Delete
    suspend fun delete(vararg elements: E)

    @Transaction
    @Update
    suspend fun update(vararg elements: E)
}