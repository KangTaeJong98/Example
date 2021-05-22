package com.taetae98.paging.base

import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

abstract class BasePagingAdapter<E: Any>(itemCallback: DiffUtil.ItemCallback<E>) : PagingDataAdapter<E, BaseHolder<out ViewDataBinding, E>>(itemCallback) {
    override fun onBindViewHolder(holder: BaseHolder<out ViewDataBinding, E>, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onBindViewHolder(holder: BaseHolder<out ViewDataBinding, E>, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        getItem(position)?.let { holder.bind(it, payloads) }
    }
}