package com.taetae98.paging

class PagingRepository {
    fun getData(page: Int): Pair<Int?, List<String>> {
        return if (page < 50) {
            page to listOf("A $page", "B $page", "C $page")
        } else {
            null to emptyList()
        }
    }
}