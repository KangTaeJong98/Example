package com.taetae98.paging.dto

import com.taetae98.paging.api.WebToonAPI
import com.taetae98.paging.protocol.WeekDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup

data class WebToon(
    val id: Long = 0L,
    val name: String = "",
    val thumbnail: String = "",
) {
    val weekDayList by lazy { ArrayList<WeekDay>() }
    val episodeList by lazy {
        runBlocking(Dispatchers.IO) {
            val api = WebToonAPI.getInstance()
            ArrayList<Episode>().apply {
                var page = 1
                while (true) {
                    val document = Jsoup.parse(api.webToonEpisodeList(id, page).charStream().readText())
                    document.select("table.viewList > tbody > tr").forEach { element ->
                        if (element.attr("class") == "") {
                            val thumbnail = element.child(0).select("img[src]").attr("src")
                            val title = element.child(1).select("a").text()
                            val episode = element.child(1).select("a").attr("href").replaceBefore("no=", "")
                                .replace("no=", "")
                                .replaceAfter("&", "")
                                .replace("&", "")
                                .toInt()

                            add(Episode(this@WebToon, title, episode, thumbnail))
                        }
                    }

                    val pages = document.select("div.page_wrap")
                    if (pages.select("a.next").isEmpty()) {
                        break
                    } else {
                        page++
                    }
                }

                reverse()
            }
        }
    }
}