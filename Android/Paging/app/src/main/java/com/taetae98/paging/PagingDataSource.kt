package com.taetae98.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlin.Exception

class PagingDataSource(
    private val repository: PagingRepository
) : PagingSource<Int, String>() {
    override fun getRefreshKey(state: PagingState<Int, String>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
        return try {
            val nextPage = params.key ?: 0
            val response = repository.getData(nextPage)
Log.d("PASS", "Data load $nextPage")
            LoadResult.Page(
                response.second,
                if (nextPage == 0) null else nextPage - 1,
                response.first?.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(Throwable("Paging Error"))
        }
    }
}