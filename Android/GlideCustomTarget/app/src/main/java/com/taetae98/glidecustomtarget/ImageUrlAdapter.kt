package com.taetae98.glidecustomtarget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.taetae98.glidecustomtarget.databinding.HolderImageUrlBinding

class ImageUrlAdapter : ListAdapter<String, ImageUrlAdapter.ImageUrlHolder>(diffCallback) {
    companion object {
        val diffCallback by lazy {
            object : DiffUtil.ItemCallback<String>() {
                override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }

                override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageUrlHolder {
        return ImageUrlHolder(
            HolderImageUrlBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageUrlHolder, position: Int) {
        holder.onBindViewHolder(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).hashCode().toLong()
    }

    inner class ImageUrlHolder(private val binding: HolderImageUrlBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBindViewHolder(imageUrl: String) {
            binding.imageUrl = imageUrl
        }
    }
}