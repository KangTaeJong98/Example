package com.taetae98.recyclerview.fragment

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.taetae98.recyclerview.GridSpacingItemDecoration
import com.taetae98.recyclerview.R
import com.taetae98.recyclerview.adapter.AlbumAdapter
import com.taetae98.recyclerview.adapter.ImageAdapter
import com.taetae98.recyclerview.base.BaseFragment
import com.taetae98.recyclerview.databinding.FragmentGalleryBinding
import com.taetae98.recyclerview.toDp
import com.taetae98.recyclerview.viewmodels.ImageViewModel

@SuppressLint("UseCompatLoadingForDrawables")
class GalleryFragment : BaseFragment<FragmentGalleryBinding>(R.layout.fragment_gallery) {
    private val imageModel by viewModels<ImageViewModel> { ImageViewModel.ImageViewModelFactory(requireContext()) }

    private val albumAdapter by lazy { AlbumAdapter().apply { submitList(imageModel.albumList) } }
    private val imageAdapter by lazy { ImageAdapter().apply { submitList(imageModel.imageList) } }

    override fun init() {
        super.init()
        initRecyclerView()
        initBottomNavigationView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.adapter = albumAdapter
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.addItemDecoration(GridSpacingItemDecoration(3, 10.toDp()))
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
    }

    private fun initBottomNavigationView() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.album -> {
                    while (binding.recyclerView.itemDecorationCount > 0) {
                        binding.recyclerView.removeItemDecorationAt(0)
                    }
                    binding.recyclerView.adapter = albumAdapter
                    binding.recyclerView.addItemDecoration(GridSpacingItemDecoration(3, 10.toDp()))
                    binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
                }
                R.id.image -> {
                    while (binding.recyclerView.itemDecorationCount > 0) {
                        binding.recyclerView.removeItemDecorationAt(0)
                    }
                    binding.recyclerView.adapter = imageAdapter
                    binding.recyclerView.addItemDecoration(GridSpacingItemDecoration(4, 3.toDp()))
                    binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 4)
                }
            }
            true
        }

        binding.bottomNavigationView.setOnNavigationItemReselectedListener {

        }
    }
}