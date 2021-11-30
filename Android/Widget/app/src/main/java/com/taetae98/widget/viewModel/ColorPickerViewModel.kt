package com.taetae98.widget.viewModel

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class ColorPickerViewModel @AssistedInject constructor(
    @Assisted
    stateHandle: SavedStateHandle,
    @Assisted
    @ColorInt
    color: Int
) : ViewModel() {
    val alpha by lazy { stateHandle.getLiveData("COLOR_PICKER_VIEW_MODEL_ALPHA", Color.alpha(color)) }
    val red by lazy { stateHandle.getLiveData("COLOR_PICKER_VIEW_MODEL_RED", Color.red(color)) }
    val green by lazy { stateHandle.getLiveData("COLOR_PICKER_VIEW_MODEL_GREEN", Color.green(color)) }
    val blue by lazy { stateHandle.getLiveData("COLOR_PICKER_VIEW_MODEL_BLUE", Color.blue(color)) }

    val color by lazy {
        MediatorLiveData<Int>().apply {
            addSource(alpha) { updateColor() }
            addSource(red) { updateColor() }
            addSource(green) { updateColor() }
            addSource(blue) { updateColor() }
        }
    }

    var onColor: ((color: Int) -> Unit)? = null

    private fun updateColor() {
        color.value = Color.argb(alpha.value!!, red.value!!, green.value!!, blue.value!!)
    }

    @AssistedFactory
    interface HiltFactory {
        fun create(stateHandle: SavedStateHandle, @ColorInt color: Int): ColorPickerViewModel
    }

    class Factory(
        private val hiltFactory: HiltFactory,
        @ColorInt
        private val color: Int,
        owner: SavedStateRegistryOwner
    ) : AbstractSavedStateViewModelFactory(owner, null) {
        override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
            return hiltFactory.create(handle, color) as T
        }
    }
}