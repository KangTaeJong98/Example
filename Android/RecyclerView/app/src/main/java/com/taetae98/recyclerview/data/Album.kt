package com.taetae98.recyclerview.data

import android.graphics.drawable.Drawable

data class Album(
        var id: Long = 0L,
        var thumbnail: Drawable? = null,
        var title: String = "",
        var count: Long = 0L
)