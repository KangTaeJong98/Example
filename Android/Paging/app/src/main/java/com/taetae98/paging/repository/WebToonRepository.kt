package com.taetae98.paging.repository

import com.taetae98.paging.api.WebToonAPI
import com.taetae98.paging.dto.WebToon
import com.taetae98.paging.protocol.WeekDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebToonRepository @Inject constructor(
    private val api: WebToonAPI
) {
    val data by lazy {
        runBlocking(Dispatchers.IO) {
            LinkedHashMap<Long, WebToon>().apply {
                for (weekDay in WeekDay.values()) {
                    Jsoup.parse(api.weekdayWebToonList(weekDay.english).charStream().readText())
                        .select("ul.img_list")
                        .select("div.thumb > a > img").forEach {
                            val name = it.attr("title")
                            val thumbnail = it.attr("src")
                            val id = thumbnail.replaceAfter("/thumbnail", "")
                                .replace("/thumbnail", "")
                                .replaceBeforeLast("/", "")
                                .replace("/", "").toLong()


                            computeIfAbsent(id) {
                                WebToon(id, name, thumbnail)
                            }.weekDayList.add(weekDay)
                        }
                }
            }
        }
    }
}