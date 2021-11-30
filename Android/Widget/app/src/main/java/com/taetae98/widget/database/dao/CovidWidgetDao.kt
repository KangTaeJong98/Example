package com.taetae98.widget.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.taetae98.modules.library.base.BaseDao
import com.taetae98.widget.dto.CovidWidget

@Dao
interface CovidWidgetDao : BaseDao<CovidWidget> {
    @Query("SELECT * FROM CovidWidget WHERE id=:id")
    suspend fun findById(id: Int): CovidWidget?

    @Query("DELETE FROM CovidWidget WHERE id in (:id)")
    suspend fun deleteByIds(id: IntArray): Int
}