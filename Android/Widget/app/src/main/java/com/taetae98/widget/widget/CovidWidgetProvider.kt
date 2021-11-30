package com.taetae98.widget.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.taetae98.widget.R
import com.taetae98.widget.database.AppDatabase
import com.taetae98.widget.dto.CovidStatusResponse
import com.taetae98.widget.dto.CovidWidget
import com.taetae98.widget.manager.CovidStatusManager
import com.taetae98.widget.repository.CovidWidgetRepository
import com.taetae98.widget.util.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import javax.inject.Inject

@AndroidEntryPoint
class CovidWidgetProvider : AppWidgetProvider() {
    @Inject
    lateinit var covidStatusManager: CovidStatusManager

    @Inject
    lateinit var covidWidgetRepository: CovidWidgetRepository

    companion object {
        fun getRemoteViews(context: Context, covidWidget: CovidWidget, covidStatusResponse: CovidStatusResponse): RemoteViews {
            val format = NumberFormat.getInstance()
            return RemoteViews(context.packageName, R.layout.widget_covid).apply {
                setInt(R.id.layout, "setBackgroundColor", covidWidget.backgroundColor)
                setTextColor(R.id.city_text_view, covidWidget.textColor)
                setTextColor(R.id.total_title_text_view, covidWidget.textColor)
                setTextColor(R.id.positive_title_text_view, covidWidget.textColor)
                setTextColor(R.id.death_title_text_view, covidWidget.textColor)
                setTextColor(R.id.total_text_view, covidWidget.textColor)
                setTextColor(R.id.positive_text_view, covidWidget.textColor)
                setTextColor(R.id.death_text_view, covidWidget.textColor)
                setTextViewText(R.id.city_text_view, covidWidget.city)
                setTextViewText(R.id.total_text_view, "${format.format(covidStatusResponse.body[covidWidget.city]?.totalPositive)}명")
                setTextViewText(R.id.positive_text_view, "${format.format(covidStatusResponse.body[covidWidget.city]?.positive)}명")
                setTextViewText(R.id.death_text_view, "${format.format(covidStatusResponse.body[covidWidget.city]?.death)}명")
            }
        }

        fun updateWidget(context: Context, covidWidget: CovidWidget, covidStatusResponse: CovidStatusResponse) {
            AppWidgetManager.getInstance(context).updateAppWidget(
                covidWidget.id, getRemoteViews(context, covidWidget, covidStatusResponse)
            )
        }

        suspend fun updateWidget(context: Context, appWidgetIds: IntArray, covidStatusResponse: CovidStatusResponse) {
            appWidgetIds.forEach { id ->
                AppDatabase.getInstance(context).covidWidgetDao().findById(id)?.let { widget ->
                    updateWidget(context, widget, covidStatusResponse)
                }
            }
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        Logger.intent("CovidWidget", intent)
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        super.onDeleted(context, appWidgetIds)
        CoroutineScope(Dispatchers.IO).launch {
            covidWidgetRepository.deleteByIds(appWidgetIds)
        }
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        covidStatusManager.onLatestCovidStatus().enqueue(
            object : Callback<CovidStatusResponse> {
                override fun onResponse(call: Call<CovidStatusResponse>, response: Response<CovidStatusResponse>) {
                    Logger.response("onLatestCovidStatus", response)
                    val covidStatusResponse = response.body() ?: return
                    CoroutineScope(Dispatchers.IO).launch {
                        updateWidget(context, appWidgetIds, covidStatusResponse)
                    }
                }

                override fun onFailure(call: Call<CovidStatusResponse>, t: Throwable) {
                    Logger.e("onLatestCovidStatus", t)
                }
            }
        )
    }
}