package com.taetae98.lifecycle.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class ChronometerViewModelFactory(private val initialTime: Long) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChronometerViewModel(initialTime) as T
    }
}