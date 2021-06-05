package com.taetae98.paging.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.taetae98.paging.R
import com.taetae98.paging.adapter.ViewerPagingAdapter
import com.taetae98.paging.base.BaseFragment
import com.taetae98.paging.databinding.FragmentViewerBinding
import com.taetae98.paging.repository.WebToonRepository
import com.taetae98.paging.utility.DataBinding
import com.taetae98.paging.viewmodel.ViewerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ViewerFragment : BaseFragment(), DataBinding<FragmentViewerBinding> {
    override val binding by lazy { DataBinding.get<FragmentViewerBinding>(this, R.layout.fragment_viewer) }

    @Inject
    lateinit var repository: WebToonRepository

    private val viewModel by viewModels<ViewerViewModel>()
    private val args by navArgs<ViewerFragmentArgs>()
    private val webToon by lazy { repository.data.getValue(args.webToonId) }
    private val episode by lazy { args.episode }

    private val viewerPagingAdapter by lazy { ViewerPagingAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewerPagingAdapter.loadStateFlow.collect {
                Log.d("PASS", it.toString())
                when {
                    it.refresh is LoadState.Loading -> {
                        /*
                        Pager is Loading
                         */
                    }

                    it.prepend is LoadState.Error ||
                            it.append is LoadState.Error ||
                            it.refresh is LoadState.Error -> {
                        /*
                        Handle Error
                         */
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.getPagingData(webToon, episode).collect {
                viewerPagingAdapter.submitData(it)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        context ?: return binding.root

        onCreateViewDataBinding()
        onCreateRecyclerView()

        return binding.root
    }

    private fun onCreateViewDataBinding() {
        binding.lifecycleOwner = this
    }

    private fun onCreateRecyclerView() {
        with(binding.recyclerView) {
            adapter = viewerPagingAdapter
        }
    }
}