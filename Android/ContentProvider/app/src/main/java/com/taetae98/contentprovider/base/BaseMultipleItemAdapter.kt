package com.taetae98.contentprovider.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseMultipleItemAdapter<T: Any>(itemCallback: DiffUtil.ItemCallback<T>) : ListAdapter<T, BaseHolder<out T>>(itemCallback)