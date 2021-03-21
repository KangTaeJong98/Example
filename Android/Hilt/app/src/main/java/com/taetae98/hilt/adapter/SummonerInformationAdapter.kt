package com.taetae98.hilt.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.taetae98.hilt.data.SummonerInformation
import com.taetae98.hilt.databinding.HolderSummonerInformationBinding
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class SummonerInformationAdapter @Inject constructor() : BaseAdapter<SummonerInformation>(SummonerInformationItemCallback()) {
    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<out ViewDataBinding, SummonerInformation> {
        return SummonerInformationHolder(HolderSummonerInformationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).entity.name.hashCode().toLong()
    }

    inner class SummonerInformationHolder(binding: HolderSummonerInformationBinding) : BaseHolder<HolderSummonerInformationBinding, SummonerInformation>(binding) {
        override fun bind(element: SummonerInformation) {
            super.bind(element)
            binding.information = element
        }
    }

    class SummonerInformationItemCallback() : DiffUtil.ItemCallback<SummonerInformation>() {
        override fun areItemsTheSame(oldItem: SummonerInformation, newItem: SummonerInformation): Boolean {
            return oldItem.entity.name == newItem.entity.name
        }

        override fun areContentsTheSame(oldItem: SummonerInformation, newItem: SummonerInformation): Boolean {
            return oldItem == newItem
        }
    }
}