package com.taetae98.databinding.fragment

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.taetae98.databinding.GridSpacingItemDecoration
import com.taetae98.databinding.R
import com.taetae98.databinding.adapter.MovieAdapter
import com.taetae98.databinding.base.BaseFragment
import com.taetae98.databinding.databinding.FragmentMovieBinding
import com.taetae98.databinding.toDp

class MovieFragment : BaseFragment<FragmentMovieBinding>(R.layout.fragment_movie) {
    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_movie_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController()) || super.onOptionsItemSelected(item)
    }

    override fun init() {
        super.init()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        with(binding.recyclerView) {
            adapter = MovieAdapter()
            addItemDecoration(GridSpacingItemDecoration(2, 5.toDp()))
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
            }
        }
    }
}