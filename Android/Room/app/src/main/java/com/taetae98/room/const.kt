package com.taetae98.room

import android.content.res.Resources

const val DATABASE_NAME = "something.db"

fun Int.toDp(): Int {
    return (this * Resources.getSystem().displayMetrics.density + 0.5F).toInt()
}