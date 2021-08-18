package com.taetae98.contentprovider.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.taetae98.contentprovider.dao.SimpleDataDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SimpleDataViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    simpleDataDao: SimpleDataDao
) : ViewModel() {
    val simpleDataLiveData by lazy { simpleDataDao.findAllLiveData() }
}