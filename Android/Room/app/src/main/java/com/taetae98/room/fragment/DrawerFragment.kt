package com.taetae98.room.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
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
import kotlinx.coroutines.withContext

class DrawerFragment : BaseFragment<FragmentDrawerBinding>(R.layout.fragment_drawer) {
    init {
        setHasOptionsMenu(true)
    }

    companion object {
        private const val MENU_DELETE = 1000
    }

    private lateinit var menu: Menu
    private val drawerAdapter by lazy { DrawerAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AppDatabase.getInstance(requireContext()).drawer().findLiveData().observe(viewLifecycleOwner) {
            drawerAdapter.submitList(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        this.menu = menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            MENU_DELETE -> {
                CoroutineScope(Dispatchers.IO).launch {
                    drawerAdapter.tracker?.selection?.forEach {
                        AppDatabase.getInstance(requireContext()).drawer().delete(
                            Drawer(id = it)
                        )
                    }

                    withContext(Dispatchers.Main) {
                        drawerAdapter.tracker?.clearSelection()
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun init() {
        super.init()
        initRecyclerView()
        initSelectionTracker()
        initOnAddButton()
    }

    private fun initRecyclerView() {
        with(binding.recyclerView) {
            adapter = drawerAdapter
            addItemDecoration(GridSpacingItemDecoration(2, 10.toDp()))
        }
    }

    private fun initSelectionTracker() {
        with(binding.recyclerView) {
            drawerAdapter.tracker = SelectionTracker.Builder(
                "Selection",
                this,
                DrawerAdapter.DrawerKeyProvider(this),
                DrawerAdapter.DrawerDetailsLookup(this),
                StorageStrategy.createLongStorage()
            ).withSelectionPredicate(
                SelectionPredicates.createSelectAnything()
            ).build().apply {
                addObserver(object : SelectionTracker.SelectionObserver<Long>() {
                    override fun onSelectionChanged() {
                        super.onSelectionChanged()
                        val tracker = this@apply
                        if (tracker.hasSelection() && menu.findItem(MENU_DELETE) == null) {
                            menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, "Delete")
                                .setIcon(R.drawable.ic_delete)
                                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
                        } else if (!tracker.hasSelection() && menu.findItem(MENU_DELETE) != null) {
                            menu.removeItem(MENU_DELETE)
                        }
                    }
                })
            }
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