package com.taetae98.flow.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FlowViewModel @Inject constructor(
    stateHandle: SavedStateHandle
) : ViewModel() {
    val data = flow {
        doLog("Flow Start")
        repeat(3) {
            delay(500L)

            doLog("Emit : $it")
            emit(it)
        }
        doLog("Flow End")
    }

    val exceptionData = flow {
        doLog("Exception Flow Start")
        repeat(10) {
            delay(500L)
            if (it > 3) {
                throw Exception("Exception : $it")
            }

            emit(it)
        }
    }

    val flowOn = flow {
        repeat(3) {
            delay(500L)
            doLog("FlowOn Send : $it")
            emit(it)
        }
    }.onEach {
        log.value += "FlowOn In ViewModel : $it"
    }.flowOn(Dispatchers.Main) // ↑ Dispatchers.Main 영향을 받음

    val log = stateHandle.getLiveData("LOG", "")

    suspend fun doLog(message: Any?) {
        withContext(Dispatchers.Main) {
            log.value += "$message\n"
        }
    }

    suspend fun clearLog() {
        withContext(Dispatchers.Main) {
            log.value = ""
        }
    }
}