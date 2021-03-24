package com.taetae98.lifecycle.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.taetae98.lifecycle.base.BaseAdapter
import com.taetae98.lifecycle.base.BaseHolder
import com.taetae98.lifecycle.databinding.HolderLogBinding

class LogAdapter : BaseAdapter<String>(LogItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<out ViewDataBinding, String> {
        return LogHolder(HolderLogBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    inner class LogHolder(binding: HolderLogBinding) : BaseHolder<HolderLogBinding, String>(binding) {
        override fun bind(element: String) {
            super.bind(element)
            binding.log = element
        }
    }

    class LogItemCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}