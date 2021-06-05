package com.taetae98.paging.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.taetae98.paging.dto.WebToon
import com.taetae98.paging.paging.ViewerPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ViewerViewModel @Inject constructor(): ViewModel() {
    fun getPagingData(webToon: WebToon, episode: Int): Flow<PagingData<String>> {
        return Pager(PagingConfig(pageSize = 10), episode - 1) {
            ViewerPagingSource(webToon, episode)
        }.flow
//                데이터를 변환해야 할 경우
//                .map {
//                    it
//                }
                .cachedIn(viewModelScope)
    }
}