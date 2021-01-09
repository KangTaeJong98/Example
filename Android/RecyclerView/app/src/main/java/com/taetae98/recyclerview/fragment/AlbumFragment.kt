package com.taetae98.recyclerview.fragment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.taetae98.recyclerview.R
import com.taetae98.recyclerview.adapter.AlbumAdapter
import com.taetae98.recyclerview.adapter.ImageAdapter
import com.taetae98.recyclerview.base.BaseFragment
import com.taetae98.recyclerview.databinding.FragmentAlbumBinding
import com.taetae98.recyclerview.viewmodels.ImageViewModel

class AlbumFragment : BaseFragment<FragmentAlbumBinding>(R.layout.fragment_album) {
    private val args by navArgs<AlbumFragmentArgs>()
    private val imageViewModel by viewModels<ImageViewModel> { ImageViewModel.ImageViewModelFactory(requireContext()) }

    override fun init() {
        super.init()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.adapter = ImageAdapter().apply { submitList(imageViewModel.imageList.filter { it.title.contains(args.albumName) }.toMutableList()) }
    }
}