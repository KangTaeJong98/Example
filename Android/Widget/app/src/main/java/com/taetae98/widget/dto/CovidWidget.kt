package com.taetae98.widget.dto

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CovidWidget(
    @PrimaryKey
    val id: Int = 0,
    val city: String = "",
    val textColor: Int = Color.WHITE,
    val backgroundColor: Int = Color.BLACK
)