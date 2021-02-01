package com.taetae98.lifecycle.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.taetae98.lifecycle.base.BaseAdapter
import com.taetae98.lifecycle.base.BaseHolder
import com.taetae98.lifecycle.data.Record
import com.taetae98.lifecycle.databinding.HolderRecordBinding

class RecordAdapter : BaseAdapter<Record>(RecordItemCallback()) {
    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<out ViewDataBinding, Record> {
        return RecordHolder(HolderRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    inner class RecordHolder(binding: HolderRecordBinding) : BaseHolder<HolderRecordBinding, Record>(binding) {
        override fun bind(element: Record) {
            super.bind(element)
            binding.record = element
        }
    }

    class RecordItemCallback : DiffUtil.ItemCallback<Record>() {
        override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
            return oldItem.time == newItem.time
        }
    }
}