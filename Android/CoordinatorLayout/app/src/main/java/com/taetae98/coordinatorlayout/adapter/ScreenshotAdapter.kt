package com.taetae98.coordinatorlayout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.taetae98.coordinatorlayout.base.BaseAdapter
import com.taetae98.coordinatorlayout.base.BaseHolder
import com.taetae98.coordinatorlayout.databinding.HolderScreenshotBinding


class ScreenshotAdapter : BaseAdapter<String>(StringItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<out ViewDataBinding, String> {
        return ScreenshotHolder(HolderScreenshotBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    inner class ScreenshotHolder(binding: HolderScreenshotBinding) : BaseHolder<HolderScreenshotBinding, String>(binding) {
        override fun bind(element: String) {
            super.bind(element)
            binding.imageUrl = element
        }
    }

    class StringItemCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}