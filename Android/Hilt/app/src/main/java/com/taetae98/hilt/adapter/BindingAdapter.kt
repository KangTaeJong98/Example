package com.taetae98.hilt.adapter

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.taetae98.hilt.R
import com.taetae98.hilt.data.LeagueEntry

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("profileIcon")
    fun setProfileIcon(view: ImageView, number: Int) {
        Log.d("PASS", "http://ddragon.leagueoflegends.com/cdn/11.6.1/img/profileicon/$number.png")
        Glide.with(view)
            .load("http://ddragon.leagueoflegends.com/cdn/11.6.1/img/profileicon/$number.png")
            .error(R.drawable.ic_profile_icon_error)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("solo_rank_tier")
    fun setSoloRankTier(view: TextView, entries: List<LeagueEntry>) {
        val entry = entries.find {
            it.queueType == "RANKED_SOLO_5x5"
        }

        if (entry == null) {
            val text = "UNRANKED"
            view.text = text
        } else {
            val text = "${entry.tier} ${entry.rank}(${entry.leaguePoints})"
            view.text = text
        }
    }

    @JvmStatic
    @BindingAdapter("team_rank_tier")
    fun setTeamRankTier(view: TextView, entries: List<LeagueEntry>) {
        val entry = entries.find {
            it.queueType == "RANKED_FLEX_SR"
        }

        if (entry == null) {
            val text = "UNRANKED"
            view.text = text
        } else {
            val text = "${entry.tier} ${entry.rank}(${entry.leaguePoints})"
            view.text = text
        }
    }

    @JvmStatic
    @BindingAdapter("solo_win_lose")
    fun setSoloWinLose(view: TextView, entries: List<LeagueEntry>) {
        val entry = entries.find {
            it.queueType == "RANKED_SOLO_5x5"
        }

        if (entry == null) {
            val text = "Win 0 / Lose 0"
            view.text = text
        } else {
            val text = "Win ${entry.wins} / Lose ${entry.losses}"
            view.text = text
        }
    }

    @JvmStatic
    @BindingAdapter("team_win_lose")
    fun setTeamWinLose(view: TextView, entries: List<LeagueEntry>) {
        val entry = entries.find {
            it.queueType == "RANKED_FLEX_SR"
        }

        if (entry == null) {
            val text = "Win 0 / Lose 0"
            view.text = text
        } else {
            val text = "Win ${entry.wins} / Lose ${entry.losses}"
            view.text = text
        }
    }
}