package com.taetae98.widget.repository

import com.taetae98.widget.database.dao.CovidWidgetDao
import com.taetae98.widget.dto.CovidWidget
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CovidWidgetRepository @Inject constructor(
    private val covidWidgetDao: CovidWidgetDao
) {
    suspend fun insert(covidWidget: CovidWidget): Long {
        return covidWidgetDao.insert(covidWidget)
    }

    suspend fun findById(id: Int): CovidWidget? {
        return covidWidgetDao.findById(id)
    }

    suspend fun deleteByIds(ids: IntArray): Int {
        return covidWidgetDao.deleteByIds(ids)
    }
}