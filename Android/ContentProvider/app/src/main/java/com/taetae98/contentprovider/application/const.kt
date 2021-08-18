package com.taetae98.contentprovider.application

import android.content.res.Resources

fun Int.toDP(): Int {
    return (this * Resources.getSystem().displayMetrics.density + 0.5F).toInt()
}