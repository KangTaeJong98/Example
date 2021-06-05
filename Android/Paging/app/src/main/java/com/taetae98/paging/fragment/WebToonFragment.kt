package com.taetae98.paging.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.taetae98.paging.R
import com.taetae98.paging.adapter.EpisodeAdapter
import com.taetae98.paging.base.BaseFragment
import com.taetae98.paging.databinding.FragmentWebToonBinding
import com.taetae98.paging.repository.WebToonRepository
import com.taetae98.paging.utility.DataBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WebToonFragment : BaseFragment(), DataBinding<FragmentWebToonBinding> {
    override val binding by lazy { DataBinding.get<FragmentWebToonBinding>(this, R.layout.fragment_web_toon) }

    @Inject
    lateinit var repository: WebToonRepository

    private val args by navArgs<WebToonFragmentArgs>()
    private val webToon by lazy { repository.data.getValue(args.webToonId) }

    private val episodeAdapter by lazy { EpisodeAdapter().apply {
        submitList(webToon.episodeList)
    } }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        context ?: return binding.root

        onCreateViewDataBinding()
        onCreateSupportActionBar()
        onCreateRecyclerView()

        return binding.root
    }

    private fun onCreateViewDataBinding() {
        binding.lifecycleOwner = this
    }

    private fun onCreateSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun onCreateRecyclerView() {
        with(binding.recyclerView) {
            adapter = episodeAdapter
            scrollToPosition(episodeAdapter.itemCount - 1)
        }
    }
}