package com.taetae98.lifecycle.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class CounterViewModel(
    private val stateHandle: SavedStateHandle,
    initialCount: Int = 0
) : ViewModel() {
    companion object {
        private const val COUNT = "count"
    }

    val counterLiveData by lazy { stateHandle.getLiveData(COUNT, initialCount) }

    fun increase() {
        stateHandle[COUNT] = (counterLiveData.value ?: 0) + 1
    }

    fun reset() {
        stateHandle[COUNT] = 0
    }
}