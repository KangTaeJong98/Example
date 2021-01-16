package com.taetae98.room.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*


@Entity(
        foreignKeys = [
            ForeignKey(
                    entity = Drawer::class,
                    parentColumns = ["name"],
                    childColumns = ["drawerName"],
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE
            )
        ]
)
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var text: String = "",
    var drawerName: String = "",
    var writtenTime: Date = Date(),
    var isFinished: Boolean = false
)