package com.taetae98.widget.api

import com.taetae98.widget.dto.CovidStatusResponse
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CovidStatusApi {
    companion object {
        private const val URL = "http://openapi.data.go.kr/openapi/service/rest/Covid19/"

        fun getInstance(): CovidStatusApi {
            return Holder.instance
        }
    }

    private object Holder {
        val instance = ApiBuilder.build<CovidStatusApi>(
            url = URL,
            factory = listOf(
                TikXmlConverterFactory.create(
                    TikXml.Builder().exceptionOnUnreadXml(false).build()
                )
            )
        )
    }

    @GET("getCovid19SidoInfStateJson")
    fun onCovidStatus(
        @Query("startCreateDt")
        begin: String,
        @Query("endCreateDt")
        end: String,
        @Query("serviceKey")
        serviceKey: String = "ntLX6nHweb1xYsxzUW2rpm6GshB+PFRQeW8gx7YRxKDSci/7eACa2/RgBMkckXS/bSK9HLR3COfPJHxB/pSBuA==",
        @Query("numOfRows")
        rows: Int = 1000,
        @Query("pageNo")
        page: Int = 1
    ): Call<CovidStatusResponse>
}