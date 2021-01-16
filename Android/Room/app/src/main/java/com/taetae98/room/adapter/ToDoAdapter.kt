package com.taetae98.room.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.taetae98.room.R
import com.taetae98.room.base.BaseAdapter
import com.taetae98.room.base.BaseHolder
import com.taetae98.room.data.ToDo
import com.taetae98.room.databinding.HolderTodoBinding

class ToDoAdapter : BaseAdapter<ToDo>(ToDoItemCallback()) {
    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<out ViewDataBinding, ToDo> {
        return ToDoHolder(HolderTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.holder_todo
    }

    class ToDoHolder(binding: HolderTodoBinding) : BaseHolder<HolderTodoBinding, ToDo>(binding) {
        override fun bind(element: ToDo) {
            super.bind(element)
            binding.todo = element
        }
    }

    class ToDoItemCallback : DiffUtil.ItemCallback<ToDo>() {
        override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
            return oldItem.drawerName == newItem.drawerName && oldItem.text == newItem.text
        }
    }
}