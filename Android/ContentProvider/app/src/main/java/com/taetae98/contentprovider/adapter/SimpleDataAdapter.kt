package com.taetae98.contentprovider.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.taetae98.contentprovider.base.BaseHolder
import com.taetae98.contentprovider.base.BaseSingleItemAdapter
import com.taetae98.contentprovider.databinding.HolderSimpleDataBinding
import com.taetae98.contentprovider.dto.SimpleData

class SimpleDataAdapter : BaseSingleItemAdapter<SimpleData>(itemCallback) {
    companion object {
        private val itemCallback by lazy {
            object : DiffUtil.ItemCallback<SimpleData>() {
                override fun areItemsTheSame(oldItem: SimpleData, newItem: SimpleData): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }

                override fun areContentsTheSame(oldItem: SimpleData, newItem: SimpleData): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<SimpleData> {
        return SimpleDataHolder(
            HolderSimpleDataBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    inner class SimpleDataHolder(private val binding: HolderSimpleDataBinding) : BaseHolder<SimpleData>(binding.root) {
        override fun onBind(element: SimpleData) {
            super.onBind(element)
            binding.simpleData = element
        }
    }
}