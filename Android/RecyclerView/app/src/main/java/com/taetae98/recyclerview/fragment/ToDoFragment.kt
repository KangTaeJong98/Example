package com.taetae98.recyclerview.fragment

import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.taetae98.recyclerview.GridSpacingItemDecoration
import com.taetae98.recyclerview.R
import com.taetae98.recyclerview.adapter.ToDoAdapter
import com.taetae98.recyclerview.base.BaseFragment
import com.taetae98.recyclerview.data.ToDo
import com.taetae98.recyclerview.databinding.FragmentTodoBinding
import com.taetae98.recyclerview.toDp
import java.util.*
import kotlin.collections.ArrayList

class ToDoFragment : BaseFragment<FragmentTodoBinding>(R.layout.fragment_todo) {
    init {
        setHasOptionsMenu(true)
    }

    private var id = 0L
    private val adapterDataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            Log.d("PASS", "onChanged")
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            super.onItemRangeChanged(positionStart, itemCount)
            Log.d("PASS", "onItemRangeChanged START: $positionStart, COUNT : $itemCount")
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            Log.d("PASS", "onItemRangeInserted POS : $positionStart, COUNT : $itemCount")
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount)
            Log.d("PASS", "onItemRangeMoved FROM : $fromPosition, TO : $toPosition, COUNT : $itemCount")

        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            Log.d("PASS", "onItemRangeRemoved POS : $positionStart, COUNT : $itemCount")
        }
    }
    private val list by lazy { ArrayList<ToDo>() }
    private val adapter by lazy { ToDoAdapter().apply { registerAdapterDataObserver(adapterDataObserver) } }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_todo_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.refresh -> {
                adapter.notifyItemRangeChanged(0, adapter.itemCount, "onRefresh")
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun init() {
        super.init()
        initRecyclerView()
        initOnAddButton()
    }

    private fun initRecyclerView() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter.apply { submitList(list) }
        binding.recyclerView.addItemDecoration(GridSpacingItemDecoration(1, 10.toDp()))
        //binding.recyclerView.addItemDecoration(DivisionItemDecoration("#FFFFFF", 5.toDp()))

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val index = binding.recyclerView.indexOfChild(viewHolder.itemView)
                list.removeAt(index)
                adapter.notifyItemRemoved(index)
            }

            override fun onMoved(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, fromPos: Int, target: RecyclerView.ViewHolder, toPos: Int, x: Int, y: Int) {
                super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y)
                Collections.swap(list, fromPos, toPos)
                adapter.notifyItemMoved(fromPos, toPos)
            }
        }).attachToRecyclerView(binding.recyclerView)
    }

    private fun initOnAddButton() {
        binding.setOnAdd {
            list.add(ToDo(id++, binding.todo.text.toString()))
            adapter.notifyItemInserted(list.lastIndex)
            binding.todo.text.clear()
        }
    }
}