package com.taetae98.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.taetae98.recyclerview.base.BaseAdapter
import com.taetae98.recyclerview.base.BaseHolder
import com.taetae98.recyclerview.databinding.HolderSnapHelperBinding


class SnapHelperAdapter : BaseAdapter<Int>(IntItemCallback()) {
    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<out ViewDataBinding, Int> {
        return SnapHelperHolder(HolderSnapHelperBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItem(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    inner class SnapHelperHolder(binding: HolderSnapHelperBinding) : BaseHolder<HolderSnapHelperBinding, Int>(binding) {
        override fun bind(element: Int) {
            super.bind(element)
            binding.value = element
        }
    }

    class IntItemCallback : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return true
        }
    }

}