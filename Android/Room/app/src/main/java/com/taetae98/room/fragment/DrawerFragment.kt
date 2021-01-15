package com.taetae98.room.fragment

import android.os.Bundle
import android.view.View
import com.taetae98.room.GridSpacingItemDecoration
import com.taetae98.room.R
import com.taetae98.room.adapter.DrawerAdapter
import com.taetae98.room.base.BaseFragment
import com.taetae98.room.data.Drawer
import com.taetae98.room.databinding.FragmentDrawerBinding
import com.taetae98.room.singleton.AppDatabase
import com.taetae98.room.toDp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DrawerFragment : BaseFragment<FragmentDrawerBinding>(R.layout.fragment_drawer) {
    private val drawerAdapter by lazy { DrawerAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AppDatabase.getInstance(requireContext()).drawer().findLiveData().observe(viewLifecycleOwner) {
            drawerAdapter.submitList(it)
        }
    }

    override fun init() {
        super.init()
        initRecyclerView()
        initOnAddButton()
    }

    private fun initRecyclerView() {
        with(binding.recyclerView) {
            adapter = drawerAdapter
            addItemDecoration(GridSpacingItemDecoration(2, 10.toDp()))
        }
    }

    private fun initOnAddButton() {
        binding.setOnAdd {
            with(binding.text) {
                val title = text.trim()
                if (title.isNotEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        AppDatabase.getInstance(requireContext()).drawer().insert(Drawer(title = title.toString()))
                        binding.text.text.clear()
                    }
                }
            }
        }
    }
}