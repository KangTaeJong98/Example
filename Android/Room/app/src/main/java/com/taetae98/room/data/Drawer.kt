package com.taetae98.room.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Drawer(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var title: String = "",
    var count: Long = 0L
)