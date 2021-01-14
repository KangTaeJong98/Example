package com.taetae98.recyclerview.fragment

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import com.taetae98.recyclerview.GridSpacingItemDecoration
import com.taetae98.recyclerview.R
import com.taetae98.recyclerview.adapter.SelectionAdapter
import com.taetae98.recyclerview.base.BaseFragment
import com.taetae98.recyclerview.data.Selection
import com.taetae98.recyclerview.databinding.FragmentSelectionBinding
import com.taetae98.recyclerview.toDp

class SelectionFragment : BaseFragment<FragmentSelectionBinding>(R.layout.fragment_selection) {
    lateinit var menu: Menu

    init {
        setHasOptionsMenu(true)
    }

    companion object {
        const val MENU_DELETE = 0
    }

    private val adapter by lazy { SelectionAdapter().apply { submitList(list) }}
    private val list by lazy {
        mutableListOf(
            Selection(0, "YES"), Selection(1, "NO"), Selection(2, "YES"),
            Selection(3, "NO"), Selection(4, "YES"), Selection(5, "YES"),
            Selection(6, "YES"), Selection(7, "YES"), Selection(8, "YES")
    ) }
    private val tracker by lazy {
        with(binding) {
            SelectionTracker.Builder(
                    "Selection",
                    recyclerView,
                    SelectionAdapter.SelectionKeyProvider(recyclerView),
                    SelectionAdapter.SelectionDetailsLookup(recyclerView),
                    StorageStrategy.createLongStorage()
            ).withSelectionPredicate(
                    SelectionAdapter.SelectionPredicate(recyclerView)
            ).build().apply {
                addObserver(object : SelectionTracker.SelectionObserver<Long>() {
                    override fun onSelectionChanged() {
                        super.onSelectionChanged()
                        val tracker = this@apply
                        if (tracker.hasSelection() && menu.findItem(MENU_DELETE) == null) {
                            menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, "Delete")
                                    .setIcon(R.drawable.ic_delete)
                                    .setOnMenuItemClickListener {
                                        tracker.selection.forEach {
                                            val holder = recyclerView.findViewHolderForItemId(it)
                                            if (holder is SelectionAdapter.SelectionHolder) {
                                                list.remove(holder.element)
                                            }
                                        }

                                        tracker.clearSelection()
                                        adapter.notifyDataSetChanged()
                                        true
                                    }
                                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
                        } else if (!tracker.hasSelection() && menu.findItem(MENU_DELETE) != null){
                            menu.removeItem(MENU_DELETE)
                        }
                    }
                })
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        this.menu = menu
    }

    override fun init() {
        super.init()
        initRecyclerView()
        initTracker()
    }

    private fun initRecyclerView() {
        binding.recyclerView.addItemDecoration(GridSpacingItemDecoration(3, 10.toDp()))
        binding.recyclerView.adapter = adapter
    }

    private fun initTracker() {
        adapter.tracker = tracker
    }
}