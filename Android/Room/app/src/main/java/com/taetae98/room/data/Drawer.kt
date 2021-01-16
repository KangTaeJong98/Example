package com.taetae98.room.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
        indices = [
            Index(value = ["name"], unique = true)
        ]
)
data class Drawer(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var name: String = "",
)