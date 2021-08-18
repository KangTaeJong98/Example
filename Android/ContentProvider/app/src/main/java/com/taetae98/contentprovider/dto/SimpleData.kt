package com.taetae98.contentprovider.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SimpleData(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String = "",
    val data: String = ""
)