package com.taetae98.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import kotlinx.coroutines.flow.map

class PagingViewModel : ViewModel() {
    val flow = Pager(PagingConfig(pageSize = 10)) {
        PagingDataSource(PagingRepository())
    }.flow.map {
        it.map { data ->
            data
        }
    }.cachedIn(viewModelScope)
}