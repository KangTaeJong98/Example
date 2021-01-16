package com.taetae98.room.base

import androidx.room.*

@Dao
interface BaseDao<E: Any> {
    @Transaction
    @Insert
    suspend fun insert(vararg elements: E)

    @Transaction
    @Insert
    suspend fun insert(elements: List<E>)

    @Transaction
    @Delete
    suspend fun delete(vararg elements: E)

    @Transaction
    @Delete
    suspend fun delete(elements: List<E>)

    @Transaction
    @Update
    suspend fun update(vararg elements: E)

    @Transaction
    @Update
    suspend fun update(elements: List<E>)
}