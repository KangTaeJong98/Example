package com.taetae98.paging.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.taetae98.paging.ActivityMainNavigationXmlDirections
import com.taetae98.paging.R
import com.taetae98.paging.base.BaseAdapter
import com.taetae98.paging.base.BaseHolder
import com.taetae98.paging.databinding.HolderEpisodeBinding
import com.taetae98.paging.dto.Episode

class EpisodeAdapter : BaseAdapter<Episode>(itemCallback) {
    companion object {
        private val itemCallback = object : DiffUtil.ItemCallback<Episode>() {
            override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem.webToon == newItem.webToon && oldItem.episode == newItem.episode
            }

            override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem == newItem
            }
        }
    }

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<out ViewDataBinding, Episode> {
        return EpisodeHolder(
            HolderEpisodeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.holder_episode
    }

    inner class EpisodeHolder(binding: HolderEpisodeBinding) : BaseHolder<HolderEpisodeBinding, Episode>(binding) {
        init {
            binding.setOnClick {
                it.findNavController().navigate(ActivityMainNavigationXmlDirections.actionGlobalViewerFragment(element.webToon.id, element.episode))
            }
        }

        override fun bind(element: Episode) {
            super.bind(element)
            binding.episode = element
        }
    }
}