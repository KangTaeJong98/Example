package com.taetae98.something.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseAdapter<E: Any>(diffCallback: DiffUtil.ItemCallback<E>) : ListAdapter<E, BaseHolder<out ViewDataBinding, E>>(diffCallback) {
    override fun onBindViewHolder(holder: BaseHolder<out ViewDataBinding, E>, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(holder: BaseHolder<out ViewDataBinding, E>, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        holder.bind(getItem(position), payloads)
    }
}