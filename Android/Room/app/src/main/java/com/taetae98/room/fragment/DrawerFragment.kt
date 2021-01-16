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
import com.taetae98.room.adapter.DrawerWithToDoAdapter
import com.taetae98.room.base.BaseFragment
import com.taetae98.room.data.Drawer
import com.taetae98.room.databinding.FragmentDrawerBinding
import com.taetae98.room.singleton.AppDatabase
import com.taetae98.room.toDp
import kotlinx.coroutines.*

class DrawerFragment : BaseFragment<FragmentDrawerBinding>(R.layout.fragment_drawer) {
    init {
        setHasOptionsMenu(true)
    }

    companion object {
        private const val MENU_DELETE = 1000
    }

    private lateinit var menu: Menu
    private val drawerWithToDoAdapter by lazy { DrawerWithToDoAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AppDatabase.getInstance(requireContext()).drawer().findLiveDataWithToDo().observe(viewLifecycleOwner) {
            drawerWithToDoAdapter.submitList(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        this.menu = menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            MENU_DELETE -> {
                drawerWithToDoAdapter.tracker?.let {
                    CoroutineScope(Dispatchers.IO).launch {
                        val deleteList = ArrayList<Drawer>(it.selection.size())
                        it.selection.forEach { id ->
                            deleteList.add(Drawer(id = id))
                        }

                        AppDatabase.getInstance(requireContext()).drawer().delete(deleteList)
                        withContext(Dispatchers.Main) {
                            it.clearSelection()
                        }
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
            adapter = drawerWithToDoAdapter
            addItemDecoration(GridSpacingItemDecoration(2, 10.toDp()))
        }
    }

    private fun initSelectionTracker() {
        with(binding.recyclerView) {
            drawerWithToDoAdapter.tracker = SelectionTracker.Builder(
                "Selection",
                this,
                DrawerWithToDoAdapter.DrawerWithToDoKeyProvider(this),
                DrawerWithToDoAdapter.DrawerWithToDoDetailsLookup(this),
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
                val name = text.trim()
                if (name.isNotEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        AppDatabase.getInstance(requireContext()).drawer().insert(Drawer(name = name.toString()))
                        withContext(Dispatchers.Main) {
                            binding.text.text.clear()
                        }
                    }
                }
            }
        }
    }
}