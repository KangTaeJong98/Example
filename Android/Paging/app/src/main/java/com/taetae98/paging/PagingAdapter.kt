package com.taetae98.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class PagingAdapter : PagingDataAdapter<String, PagingAdapter.PagingHolder>(diffUtil) {
    override fun onBindViewHolder(holder: PagingHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingHolder {
        return PagingHolder(LayoutInflater.from(parent.context).inflate(R.layout.holder_paging, parent, false))
    }

    inner class PagingHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(value: String?) {
            itemView.findViewById<TextView>(R.id.text).text = value
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}