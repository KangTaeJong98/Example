package com.taetae98.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.taetae98.recyclerview.R
import com.taetae98.recyclerview.base.BaseAdapter
import com.taetae98.recyclerview.base.BaseHolder
import com.taetae98.recyclerview.data.Image
import com.taetae98.recyclerview.databinding.HolderImageBinding

class ImageAdapter : BaseAdapter<Image>(ImageItemCallback()) {
    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<out ViewDataBinding, Image> {
        return ImageHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.holder_image
    }

    private class ImageHolder(binding: HolderImageBinding) : BaseHolder<HolderImageBinding, Image>(binding) {
        override fun bind(element: Image) {
            super.bind(element)
            binding.image = element
        }
    }

    private class ImageItemCallback : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return false
        }
    }
}