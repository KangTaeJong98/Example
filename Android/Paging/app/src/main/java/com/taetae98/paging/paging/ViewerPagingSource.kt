package com.taetae98.paging.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.taetae98.paging.dto.WebToon

class ViewerPagingSource(
        private val webToon: WebToon,
        private val episode: Int
) : PagingSource<Int, String>() {
    override fun getRefreshKey(state: PagingState<Int, String>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
        return try {
            val page = (params.key ?: episode - 1)

            LoadResult.Page(webToon.episodeList[page].imageList, null, if (page == webToon.episodeList.size) null else page + 1)
        } catch (e: Exception) {
            LoadResult.Error(Throwable("Paging Error"))
        }
    }
}