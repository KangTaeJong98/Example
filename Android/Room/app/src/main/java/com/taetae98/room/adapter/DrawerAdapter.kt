package com.taetae98.room.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.taetae98.room.R
import com.taetae98.room.base.BaseAdapter
import com.taetae98.room.base.BaseHolder
import com.taetae98.room.data.Drawer
import com.taetae98.room.databinding.HolderDrawerBinding

class DrawerAdapter : BaseAdapter<Drawer>(DrawerDiffItemCallback()) {
    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<out ViewDataBinding, Drawer> {
        return DrawerHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.holder_drawer
    }

    inner class DrawerHolder(binding: HolderDrawerBinding) : BaseHolder<HolderDrawerBinding, Drawer>(binding) {
        override fun bind(element: Drawer) {
            super.bind(element)
            binding.drawer = element
        }
    }

    class DrawerDiffItemCallback : DiffUtil.ItemCallback<Drawer>() {
        override fun areItemsTheSame(oldItem: Drawer, newItem: Drawer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Drawer, newItem: Drawer): Boolean {
            return oldItem.title == newItem.title && oldItem.count == newItem.count
        }
    }
}