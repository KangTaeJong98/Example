package com.taetae98.recyclerview.fragment

import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.taetae98.recyclerview.GridSpacingItemDecoration
import com.taetae98.recyclerview.R
import com.taetae98.recyclerview.adapter.SnapHelperAdapter
import com.taetae98.recyclerview.base.BaseFragment
import com.taetae98.recyclerview.databinding.FragmentSnapHelperBinding
import com.taetae98.recyclerview.toDp

class SnapHelperFragment : BaseFragment<FragmentSnapHelperBinding>(R.layout.fragment_snap_helper) {
    override fun init() {
        super.init()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val snapHelper = LinearSnapHelper()

        with(binding.recyclerView) {
            adapter = SnapHelperAdapter()
            snapHelper.attachToRecyclerView(this)
            addItemDecoration(GridSpacingItemDecoration(1, 10.toDp(), RecyclerView.HORIZONTAL))
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                var currentPosition = RecyclerView.NO_POSITION

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (recyclerView.layoutManager != null) {
                        val view = snapHelper.findSnapView(recyclerView.layoutManager)!!
                        val position = recyclerView.layoutManager!!.getPosition(view)

                        if (currentPosition != position) {
                            currentPosition = position
                            binding.position = currentPosition
                        }
                    }
                }
            })
        }
    }
}