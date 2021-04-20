package com.taetae98.databinding.fragment

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.taetae98.databinding.GridSpacingItemDecoration
import com.taetae98.databinding.R
import com.taetae98.databinding.adapter.MovieAdapter
import com.taetae98.databinding.base.BaseFragment
import com.taetae98.databinding.databinding.FragmentSearchBinding
import com.taetae98.databinding.toDp

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val viewModel by viewModels<SearchFragmentViewModel>()
    private val movieAdapter by lazy { MovieAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.search.observe(this) {
            with(binding) {
                textInputLayout.helperText = if (it.isBlank()) {
                    ""
                } else {
                    "Result of '$it'"
                }
            }

            movieAdapter.filter(it)
        }
    }

    override fun init() {
        super.init()
        binding.viewModel = viewModel
        initRecyclerView()
    }

    private fun initRecyclerView() {
        with(binding.recyclerView) {
            adapter = movieAdapter
            addItemDecoration(GridSpacingItemDecoration(2, 5.toDp()))
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
            }
        }
    }

    class SearchFragmentViewModel : ViewModel() {
        val search by lazy { MutableLiveData("") }
    }
}