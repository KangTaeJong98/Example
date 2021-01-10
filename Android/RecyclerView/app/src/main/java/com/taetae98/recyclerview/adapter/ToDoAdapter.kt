package com.taetae98.recyclerview.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.taetae98.recyclerview.R
import com.taetae98.recyclerview.base.BaseAdapter
import com.taetae98.recyclerview.base.BaseHolder
import com.taetae98.recyclerview.data.ToDo
import com.taetae98.recyclerview.databinding.HolderTodoBinding
import kotlin.properties.Delegates


class ToDoAdapter : BaseAdapter<ToDo>(ToDoItemCallback()) {
    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<out ViewDataBinding, ToDo> {
        return ToDoHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.holder_todo
    }

    inner class ToDoHolder(binding: HolderTodoBinding) : BaseHolder<HolderTodoBinding, ToDo>(binding) {
        private var isFinished by Delegates.observable(false) { _, _, newValue ->
            if (newValue) {
                with(binding.isFinished) {
                    animate()
                            .withStartAction {
                                visibility = View.VISIBLE
                            }
                            .alpha(1F)
                            .scaleX(1F)
                            .scaleY(1F)
                            .setDuration(500)
                            .setInterpolator(OvershootInterpolator())
                }
            } else {
                with(binding.isFinished) {
                    animate()
                            .alpha(0F)
                            .scaleX(0F)
                            .scaleY(0F)
                            .setDuration(500)
                            .withEndAction {
                                visibility = View.GONE
                            }
                }
            }
        }

        init {
            itemView.setOnClickListener {
                this@ToDoAdapter.notifyItemChanged(adapterPosition, "onClick")
            }
        }

        private fun onClick() {
            isFinished = !isFinished
        }

        private fun onRefresh() {
            isFinished = false
        }

        override fun bind(element: ToDo) {
            Log.d("PASS", "bind $element")
            super.bind(element)
            binding.todo = element
        }

        override fun bind(element: ToDo, payload: MutableList<Any>) {
            Log.d("PASS", "bind with payload $element, $payload")
            super.bind(element, payload)
            for (any in payload) {
                when(any) {
                    "onClick" -> {
                        onClick()
                    }
                    "onRefresh" -> {
                        onRefresh()
                    }
                }
            }
        }
    }

    private class ToDoItemCallback : DiffUtil.ItemCallback<ToDo>() {
        override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
            return oldItem.text == newItem.text
        }
    }
}