package com.taetae98.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.taetae98.recyclerview.R
import com.taetae98.recyclerview.base.BaseAdapter
import com.taetae98.recyclerview.base.BaseHolder
import com.taetae98.recyclerview.data.Album
import com.taetae98.recyclerview.databinding.HolderAlbumBinding
import com.taetae98.recyclerview.fragment.AlbumFragmentArgs
import com.taetae98.recyclerview.fragment.AlbumFragmentDirections

class AlbumAdapter : BaseAdapter<Album>(AlbumItemDiff()) {
    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<out ViewDataBinding, Album> {
        return AlbumHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.holder_album
    }

    private class AlbumHolder(binding: HolderAlbumBinding) : BaseHolder<HolderAlbumBinding, Album>(binding) {
        init {
            binding.setOnAlbumClick {
                binding.root.findNavController().navigate(AlbumFragmentDirections.actionGlobalAlbumFragment(element.title))
            }
        }
        override fun bind(element: Album) {
            super.bind(element)
            binding.gallery = element
        }
    }

    private class AlbumItemDiff : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.title == newItem.title
        }
    }
}