package com.taetae98.contentprovider.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.taetae98.contentprovider.dao.SimpleDataDao
import com.taetae98.contentprovider.dto.SimpleData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SimpleDataEditViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val simpleDataDao: SimpleDataDao
) : ViewModel() {
    val title by lazy { stateHandle.getLiveData("TITLE", "") }
    val data by lazy { stateHandle.getLiveData("DATA", "") }

    fun onCommit() {
        CoroutineScope(Dispatchers.IO).launch {
            simpleDataDao.insert(
                SimpleData(
                    title = title.value!!,
                    data = data.value!!
                )
            )
        }
    }
}