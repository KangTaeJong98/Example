package com.taetae98.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.taetae98.recyclerview.R
import com.taetae98.recyclerview.base.BaseAdapter
import com.taetae98.recyclerview.base.BaseHolder
import com.taetae98.recyclerview.data.Chat
import com.taetae98.recyclerview.databinding.HolderMyChatBinding
import com.taetae98.recyclerview.databinding.HolderOtherChatBinding

class ChatAdapter : BaseAdapter<Chat>(ChatDiffCallback()) {
    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<out ViewDataBinding, Chat> {
        return when(viewType) {
            R.layout.holder_my_chat -> {
                MyChatHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false))
            }
            R.layout.holder_other_chat -> {
                OtherChatHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false))
            }
            else -> {
                throw IllegalStateException("Illegal viewType : $viewType")
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position).writer) {
            "My" -> {
                R.layout.holder_my_chat
            }
            else -> {
                R.layout.holder_other_chat
            }
        }
    }

    class MyChatHolder(binding: HolderMyChatBinding) : BaseHolder<HolderMyChatBinding, Chat>(binding) {
        override fun bind(element: Chat) {
            super.bind(element)
            binding.chat = element
        }
    }

    class OtherChatHolder(binding: HolderOtherChatBinding) : BaseHolder<HolderOtherChatBinding, Chat>(binding) {
        override fun bind(element: Chat) {
            super.bind(element)
            binding.chat = element
        }
    }

    private class ChatDiffCallback : DiffUtil.ItemCallback<Chat>() {
        override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem.writer == newItem.writer && oldItem.text == newItem.text
        }
    }
}