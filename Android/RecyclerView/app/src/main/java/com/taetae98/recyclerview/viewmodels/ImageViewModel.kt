package com.taetae98.recyclerview.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.taetae98.recyclerview.R
import com.taetae98.recyclerview.data.Album
import com.taetae98.recyclerview.data.Image

@SuppressLint("UseCompatLoadingForDrawables")
class ImageViewModel(private val context: Context) : ViewModel() {
    val imageList by lazy { listOf(
            Image(0L, "IU1", context.resources.getDrawable(R.drawable.iu1, null)),
            Image(1L, "IU2", context.resources.getDrawable(R.drawable.iu2, null)),
            Image(2L, "IU3", context.resources.getDrawable(R.drawable.iu3, null)),
            Image(3L, "IU4", context.resources.getDrawable(R.drawable.iu4, null)),
            Image(4L, "퀸즈 갬빗1", context.resources.getDrawable(R.drawable.queensgambit1, null)),
            Image(5L, "선미1", context.resources.getDrawable(R.drawable.sunmi1, null)),
            Image(6L, "백예린1", context.resources.getDrawable(R.drawable.yerin1, null)),
            Image(7L, "백예린2", context.resources.getDrawable(R.drawable.yerin2, null)),
    ) }

    val albumList by lazy { listOf(
            Album(0L, context.resources.getDrawable(R.drawable.iu1, null), "IU", 4),
            Album(1L, context.resources.getDrawable(R.drawable.queensgambit1, null), "퀸즈 갬빗", 1),
            Album(2L, context.resources.getDrawable(R.drawable.sunmi1, null), "선미", 1),
            Album(3L, context.resources.getDrawable(R.drawable.yerin1, null), "백예린", 2),
    ) }

    class ImageViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ImageViewModel(context) as T
        }
    }
}