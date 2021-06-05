package com.taetae98.paging.dto

import com.taetae98.paging.api.WebToonAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup

data class Episode(
    val webToon: WebToon,
    val title: String,
    val episode: Int,
    val thumbnail: String
) {
    val id: Long
        get() {
            return "${webToon.name}$episode".hashCode().toLong()
        }

    val imageList by lazy {
        runBlocking(Dispatchers.IO) {
            val api = WebToonAPI.getInstance()
            val document = Jsoup.parse(api.viewer(webToon.id, episode).charStream().readText())

            document.select("div.wt_viewer > img[src]").map {
                it.attr("src")
            }
        }
    }
}