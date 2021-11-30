package com.taetae98.widget.manager

import com.taetae98.widget.api.CovidStatusApi
import com.taetae98.widget.dto.CovidStatusResponse
import retrofit2.Call
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CovidStatusManager @Inject constructor(
    private val covidStatusApi: CovidStatusApi
) {
    private fun onCovidStatus(begin: Long, end: Long): Call<CovidStatusResponse> {
        val format = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        return covidStatusApi.onCovidStatus(
            begin = format.format(begin),
            end = format.format(end)
        )
    }

    fun onLatestCovidStatus(): Call<CovidStatusResponse> {
        val begin = GregorianCalendar().apply { add(Calendar.DATE, -10) }
        val end = GregorianCalendar()

        return onCovidStatus(
            begin = begin.timeInMillis,
            end = end.timeInMillis
        )
    }
}