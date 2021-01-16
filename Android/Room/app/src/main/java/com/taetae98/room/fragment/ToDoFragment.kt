package com.taetae98.room.fragment

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.taetae98.room.GridSpacingItemDecoration
import com.taetae98.room.R
import com.taetae98.room.adapter.ToDoAdapter
import com.taetae98.room.base.BaseFragment
import com.taetae98.room.data.Drawer
import com.taetae98.room.data.ToDo
import com.taetae98.room.databinding.FragmentTodoBinding
import com.taetae98.room.singleton.AppDatabase
import com.taetae98.room.toDp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates

class ToDoFragment : BaseFragment<FragmentTodoBinding>(R.layout.fragment_todo) {
    init {
        setHasOptionsMenu(true)
    }

    private val args by navArgs<ToDoFragmentArgs>()
    private val drawerName by lazy { args.drawerName }
    private val todoAdapter by lazy { ToDoAdapter() }

    private var filter by Delegates.observable(false) { _, _, _ ->
        submitList()
    }
    private var currentList = emptyList<ToDo>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AppDatabase.getInstance(requireContext()).todo().findLiveDataByDrawerName(drawerName).observe(viewLifecycleOwner) {
            currentList = it
            submitList()
        }
        AppDatabase.getInstance(requireContext()).todo().findLiveDataMinimal().observe(viewLifecycleOwner) {
            Log.d("LOG_ROOM", it.toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_todo_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.finished -> {
                filter = true
            }
            R.id.notFinished -> {
                filter = false
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun submitList() {
        todoAdapter.submitList(currentList.filter { it.isFinished == filter })
    }

    override fun init() {
        super.init()
        initToolbar()
        initRecyclerView()
        initOnAddButton()
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).supportActionBar?.title = "$drawerName Drawer"
    }

    private fun initRecyclerView() {
        with(binding.recyclerView) {
            adapter = todoAdapter
            addItemDecoration(GridSpacingItemDecoration(1, 10.toDp()))

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    return viewHolder is ToDoAdapter.ToDoHolder
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    if (viewHolder is ToDoAdapter.ToDoHolder) {
                        if (!viewHolder.element.isFinished) {
                            CoroutineScope(Dispatchers.IO).launch {
                                viewHolder.element.isFinished = true
                                AppDatabase.getInstance(requireContext()).todo().update(viewHolder.element)
                            }
                        } else {
                            CoroutineScope(Dispatchers.IO).launch {
                                when(direction) {
                                    ItemTouchHelper.RIGHT -> {
                                        AppDatabase.getInstance(requireContext()).todo().delete(viewHolder.element)
                                    }
                                    ItemTouchHelper.LEFT -> {
                                        viewHolder.element.isFinished = false
                                        AppDatabase.getInstance(requireContext()).todo().update(viewHolder.element)
                                    }
                                }
                            }
                        }
                    }
                }
            }).attachToRecyclerView(this)
        }
    }

    private fun initOnAddButton() {
        binding.setOnAdd {
            with(binding.text) {
                val name = text.trim()
                if (name.isNotEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        AppDatabase.getInstance(requireContext()).todo().insert(ToDo(text = name.toString(), drawerName = drawerName))
                        withContext(Dispatchers.Main) {
                            binding.text.text.clear()
                        }
                    }
                }
            }
        }
    }
}