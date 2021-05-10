package com.taetae98.glide.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.taetae98.glide.base.BaseAdapter
import com.taetae98.glide.base.BaseHolder
import com.taetae98.glide.databinding.HolderImageBinding

class ImageUriAdapter : BaseAdapter<String>(StringItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<out ViewDataBinding, String> {
        return ImageHolder(HolderImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    inner class ImageHolder(binding: HolderImageBinding) : BaseHolder<HolderImageBinding, String>(binding) {
        override fun bind(element: String) {
            super.bind(element)
            binding.imageUri = element
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