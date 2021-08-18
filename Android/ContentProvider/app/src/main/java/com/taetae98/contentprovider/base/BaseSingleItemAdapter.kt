package com.taetae98.contentprovider.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseSingleItemAdapter<T: Any>(itemCallback: DiffUtil.ItemCallback<T>) : ListAdapter<T, BaseHolder<T>>(itemCallback) {
    override fun onBindViewHolder(holder: BaseHolder<T>, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun onBindViewHolder(holder: BaseHolder<T>, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        holder.onPayload(getItem(position), payloads)
    }
}