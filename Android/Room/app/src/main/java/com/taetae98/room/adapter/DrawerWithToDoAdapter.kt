package com.taetae98.room.adapter

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.taetae98.room.R
import com.taetae98.room.base.BaseAdapter
import com.taetae98.room.base.BaseHolder
import com.taetae98.room.data.Drawer
import com.taetae98.room.data.DrawerWithToDo
import com.taetae98.room.databinding.HolderDrawerBinding
import com.taetae98.room.fragment.DrawerFragmentDirections

class DrawerWithToDoAdapter : BaseAdapter<DrawerWithToDo>(DrawerWithToDoItemCallback()) {
    var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<out ViewDataBinding, DrawerWithToDo> {
        return DrawerWithToDoHolder(HolderDrawerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).drawer.id
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.holder_drawer
    }

    inner class DrawerWithToDoHolder(binding: HolderDrawerBinding) : BaseHolder<HolderDrawerBinding, DrawerWithToDo>(binding) {
        init {
            itemView.setOnClickListener {
                it.findNavController().navigate(DrawerFragmentDirections.actionDrawerFragmentToToDoFragment(element.drawer.name))
            }
        }

        override fun bind(element: DrawerWithToDo) {
            super.bind(element)
            binding.drawer = element

            itemView.isActivated = tracker?.isSelected(itemId) ?: false
        }
    }

    class DrawerWithToDoItemCallback : DiffUtil.ItemCallback<DrawerWithToDo>() {
        override fun areItemsTheSame(oldItem: DrawerWithToDo, newItem: DrawerWithToDo): Boolean {
            return oldItem.drawer.name == newItem.drawer.name
        }

        override fun areContentsTheSame(oldItem: DrawerWithToDo, newItem: DrawerWithToDo): Boolean {
            return oldItem.todoList == newItem.todoList
        }
    }

    class DrawerWithToDoKeyProvider(private val recyclerView: RecyclerView) : ItemKeyProvider<Long>(SCOPE_CACHED) {
        override fun getKey(position: Int): Long {
            val holder = recyclerView.findViewHolderForAdapterPosition(position)
            return holder?.itemId ?: throw IllegalStateException("No Holder")
        }

        override fun getPosition(key: Long): Int {
            val holder = recyclerView.findViewHolderForItemId(key)
            return if (holder is DrawerWithToDoHolder) {
                holder.adapterPosition
            } else {
                RecyclerView.NO_POSITION
            }
        }
    }

    class DrawerWithToDoDetailsLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {
        override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
            val view = recyclerView.findChildViewUnder(e.x, e.y) ?: return null
            val holder = recyclerView.getChildViewHolder(view)

            return if (holder is DrawerWithToDoHolder) {
                object : ItemDetails<Long>() {
                    override fun getPosition(): Int {
                        return holder.adapterPosition
                    }

                    override fun getSelectionKey(): Long {
                        return holder.itemId
                    }
                }
            } else {
                null
            }
        }
    }
}