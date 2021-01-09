package com.taetae98.recyclerview.data

import android.graphics.drawable.Drawable

data class Image(
        var id: Long = 0L,
        var title: String = "",
        var image: Drawable? = null
)