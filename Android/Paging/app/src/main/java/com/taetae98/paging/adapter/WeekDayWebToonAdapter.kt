package com.taetae98.paging.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.taetae98.paging.R
import com.taetae98.paging.base.BaseAdapter
import com.taetae98.paging.base.BaseHolder
import com.taetae98.paging.databinding.HolderWeekDayWebToonBinding
import com.taetae98.paging.protocol.WeekDay
import com.taetae98.paging.repository.WebToonRepository
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class WeekDayWebToonAdapter @Inject constructor(
    private val repository: WebToonRepository
) : BaseAdapter<WeekDay>(itemCallback){
    companion object {
        private val itemCallback = object : DiffUtil.ItemCallback<WeekDay>() {
            override fun areItemsTheSame(oldItem: WeekDay, newItem: WeekDay): Boolean {
                return oldItem.ordinal == newItem.ordinal
            }

            override fun areContentsTheSame(oldItem: WeekDay, newItem: WeekDay): Boolean {
                return oldItem == newItem
            }
        }
    }

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<out ViewDataBinding, WeekDay> {
        return WeekDayWebToonHolder(
            HolderWeekDayWebToonBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getCurrentList(): MutableList<WeekDay> {
        return WeekDay.values().toMutableList()
    }

    override fun getItem(position: Int): WeekDay {
        return currentList[position]
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).ordinal.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.holder_week_day_web_toon
    }

    inner class WeekDayWebToonHolder(binding: HolderWeekDayWebToonBinding) : BaseHolder<HolderWeekDayWebToonBinding, WeekDay>(binding) {
        private val webToonAdapter by lazy { WebToonAdapter() }

        init {
            binding.recyclerView.adapter = webToonAdapter
        }

        override fun bind(element: WeekDay) {
            super.bind(element)
            webToonAdapter.submitList(
                repository.data.values.filter {
                    it.weekDayList.contains(element)
                }
            )
        }
    }
}