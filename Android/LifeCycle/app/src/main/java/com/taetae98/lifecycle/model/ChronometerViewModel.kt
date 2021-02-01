package com.taetae98.lifecycle.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taetae98.lifecycle.data.Record

class ChronometerViewModel(initialTime: Long = 0L) : ViewModel() {
    var sumOfTickTime = initialTime
    var lastTickTime = 0L

    val recordList by lazy { ArrayList<Record>() }

    val state by lazy { MutableLiveData(State.RESET) }

    enum class State {
        RESET, START, RUN, STOP, RESUME
    }
}