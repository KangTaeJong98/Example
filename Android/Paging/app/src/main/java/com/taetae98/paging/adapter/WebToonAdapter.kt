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
import com.taetae98.paging.databinding.HolderWebToonBinding
import com.taetae98.paging.dto.WebToon

class WebToonAdapter : BaseAdapter<WebToon>(itemCallback) {
    companion object {
        private val itemCallback = object : DiffUtil.ItemCallback<WebToon>() {
            override fun areItemsTheSame(oldItem: WebToon, newItem: WebToon): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: WebToon, newItem: WebToon): Boolean {
                return oldItem == newItem
            }
        }
    }

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<out ViewDataBinding, WebToon> {
        return WebToonHolder(
            HolderWebToonBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.holder_web_toon
    }

    inner class WebToonHolder(binding: HolderWebToonBinding) : BaseHolder<HolderWebToonBinding, WebToon>(binding) {
        init {
            binding.setOnClick {
                it.findNavController().navigate(ActivityMainNavigationXmlDirections.actionGlobalWebToonFragment(element.id))
            }
        }

        override fun bind(element: WebToon) {
            super.bind(element)
            binding.webToon = element
        }
    }
}