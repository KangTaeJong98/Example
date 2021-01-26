package com.taetae98.coordinatorlayout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.taetae98.coordinatorlayout.ActivityMainNavigationDirections
import com.taetae98.coordinatorlayout.base.BaseAdapter
import com.taetae98.coordinatorlayout.base.BaseHolder
import com.taetae98.coordinatorlayout.data.Movie
import com.taetae98.coordinatorlayout.databinding.HolderMovieBinding

class MovieAdapter : BaseAdapter<Movie>(MovieItemCallback()) {
    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<out ViewDataBinding, Movie> {
        return MovieHolder(HolderMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    inner class MovieHolder(binding: HolderMovieBinding) : BaseHolder<HolderMovieBinding, Movie>(binding) {
        init {
            binding.setOnClick {
                it.findNavController().navigate(ActivityMainNavigationDirections.actionGlobalInformationFragment(element))
            }
        }

        override fun bind(element: Movie) {
            super.bind(element)
            binding.movie = element
        }
    }

    class MovieItemCallback() : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.imageUrl == newItem.imageUrl
        }
    }
}