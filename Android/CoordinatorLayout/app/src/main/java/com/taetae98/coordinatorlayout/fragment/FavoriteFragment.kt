package com.taetae98.coordinatorlayout.fragment

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.taetae98.coordinatorlayout.GridSpacingItemDecoration
import com.taetae98.coordinatorlayout.R
import com.taetae98.coordinatorlayout.adapter.MovieAdapter
import com.taetae98.coordinatorlayout.base.BaseFragment
import com.taetae98.coordinatorlayout.data.MovieMemoryData
import com.taetae98.coordinatorlayout.databinding.FragmentFavoriteBinding
import com.taetae98.coordinatorlayout.toDp

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(R.layout.fragment_favorite){
    override fun init() {
        super.init()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        with(binding.recyclerView) {
            adapter = MovieAdapter().apply {
                submitList(MovieMemoryData.movieList.filter {
                    it.isFavorite
                })
            }
            addItemDecoration(GridSpacingItemDecoration(2, 5.toDp()))
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
            }
        }
    }
}