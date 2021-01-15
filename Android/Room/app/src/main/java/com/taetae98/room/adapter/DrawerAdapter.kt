package com.taetae98.room.adapter

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.taetae98.room.R
import com.taetae98.room.base.BaseAdapter
import com.taetae98.room.base.BaseHolder
import com.taetae98.room.data.Drawer
import com.taetae98.room.databinding.HolderDrawerBinding

class DrawerAdapter : BaseAdapter<Drawer>(DrawerDiffItemCallback()) {
    var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<out ViewDataBinding, Drawer> {
        return DrawerHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.holder_drawer
    }

    inner class DrawerHolder(binding: HolderDrawerBinding) : BaseHolder<HolderDrawerBinding, Drawer>(binding) {
        override fun bind(element: Drawer) {
            super.bind(element)
            binding.drawer = element

            itemView.isActivated = tracker?.isSelected(itemId) ?: false
        }
    }

    class DrawerDiffItemCallback : DiffUtil.ItemCallback<Drawer>() {
        override fun areItemsTheSame(oldItem: Drawer, newItem: Drawer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Drawer, newItem: Drawer): Boolean {
            return oldItem.title == newItem.title && oldItem.count == newItem.count
        }
    }

    class DrawerKeyProvider(private val recyclerView: RecyclerView) : ItemKeyProvider<Long>(SCOPE_CACHED) {
        override fun getKey(position: Int): Long {
            val holder = recyclerView.findViewHolderForAdapterPosition(position)
            return holder?.itemId ?: throw IllegalStateException("No Holder")
        }

        override fun getPosition(key: Long): Int {
            val holder = recyclerView.findViewHolderForItemId(key)
            return if (holder is DrawerHolder) {
                holder.adapterPosition
            } else {
                RecyclerView.NO_POSITION
            }
        }
    }

    class DrawerDetailsLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {
        override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
            val view = recyclerView.findChildViewUnder(e.x, e.y) ?: return null
            val holder = recyclerView.getChildViewHolder(view)

            return if (holder is DrawerHolder) {
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