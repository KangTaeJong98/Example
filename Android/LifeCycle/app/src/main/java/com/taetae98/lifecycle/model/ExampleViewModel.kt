package com.taetae98.lifecycle.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ExampleViewModel : ViewModel() {
    init {
        viewModelScope.launch {
            /*
            ViewModel이 사라지면 종료된다.
             */
        }
    }
}