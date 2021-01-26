package com.taetae98.coordinatorlayout.fragment

import android.util.Log
import androidx.navigation.fragment.navArgs
import com.taetae98.coordinatorlayout.GridSpacingItemDecoration
import com.taetae98.coordinatorlayout.R
import com.taetae98.coordinatorlayout.adapter.MovieAdapter
import com.taetae98.coordinatorlayout.base.BaseFragment
import com.taetae98.coordinatorlayout.data.MovieMemoryData
import com.taetae98.coordinatorlayout.databinding.FragmentInformationBinding
import com.taetae98.coordinatorlayout.toDp

class InformationFragment : BaseFragment<FragmentInformationBinding>(R.layout.fragment_information) {
    private val args by navArgs<InformationFragmentArgs>()

    override fun init() {
        super.init()
        initMovie()
        initOnFavoriteButton()
        initRecyclerView()
    }

    private fun initMovie() {
        binding.movie = args.movie
    }

    private fun initOnFavoriteButton() {
        binding.setOnFavorite {
            args.movie.isFavorite = true
            binding.fab.hide()
        }
    }

    private fun initRecyclerView() {
        Log.d("PASSZ", "init")
        with(binding.recyclerView) {
            adapter = MovieAdapter().apply {
                submitList(MovieMemoryData.movieList)
            }
            addItemDecoration(GridSpacingItemDecoration(2, 5.toDp()))
        }
    }
}