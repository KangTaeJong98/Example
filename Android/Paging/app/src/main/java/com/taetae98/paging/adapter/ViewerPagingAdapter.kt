package com.taetae98.paging.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.taetae98.paging.R
import com.taetae98.paging.base.BaseHolder
import com.taetae98.paging.base.BasePagingAdapter
import com.taetae98.paging.databinding.HolderViewerBinding

class ViewerPagingAdapter : BasePagingAdapter<String>(itemCallback) {
    companion object {
        private val itemCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<out ViewDataBinding, String> {
        return ViewerHolder(
            HolderViewerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.holder_viewer
    }

    inner class ViewerHolder(binding: HolderViewerBinding) : BaseHolder<HolderViewerBinding, String>(binding) {
        override fun bind(element: String) {
            super.bind(element)
            binding.imageUrl = element
        }
    }
}