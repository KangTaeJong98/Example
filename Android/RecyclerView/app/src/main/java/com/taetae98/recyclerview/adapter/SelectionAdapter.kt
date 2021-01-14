package com.taetae98.recyclerview.adapter

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
import com.taetae98.recyclerview.R
import com.taetae98.recyclerview.base.BaseAdapter
import com.taetae98.recyclerview.base.BaseHolder
import com.taetae98.recyclerview.data.Selection
import com.taetae98.recyclerview.databinding.HolderSelectionBinding

class SelectionAdapter : BaseAdapter<Selection>(SelectionItemCallback()) {
    var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<out ViewDataBinding, Selection> {
        return SelectionHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.holder_selection
    }

    inner class SelectionHolder(binding: HolderSelectionBinding) : BaseHolder<HolderSelectionBinding, Selection>(binding) {
        override fun bind(element: Selection) {
            super.bind(element)
            binding.selection = element

            itemView.isActivated = tracker?.isSelected(itemId) ?: false
        }
    }

    class SelectionKeyProvider(private val recyclerView: RecyclerView) : ItemKeyProvider<Long>(SCOPE_MAPPED) {
        override fun getKey(position: Int): Long {
            val holder = recyclerView.findViewHolderForAdapterPosition(position)
            return holder?.itemId ?: throw IllegalStateException("No Holder")
        }

        override fun getPosition(key: Long): Int {
            val holder = recyclerView.findViewHolderForItemId(key)
            return if (holder is SelectionAdapter.SelectionHolder) {
                holder.adapterPosition
            } else {
                RecyclerView.NO_POSITION
            }
        }
    }

    class SelectionDetailsLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {
        override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
            val view = recyclerView.findChildViewUnder(e.x, e.y) ?: return null

            val holder = recyclerView.getChildViewHolder(view)
            return if (holder is SelectionHolder) {
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

    class SelectionPredicate(private val recyclerView: RecyclerView) : SelectionTracker.SelectionPredicate<Long>() {
        override fun canSetStateForKey(key: Long, nextState: Boolean): Boolean {
            val holder = recyclerView.findViewHolderForItemId(key)
            return if (holder is SelectionAdapter.SelectionHolder) {
                holder.element.text == "YES"
            } else {
                false
            }
        }

        override fun canSetStateAtPosition(position: Int, nextState: Boolean): Boolean {
            return true
        }

        override fun canSelectMultiple(): Boolean {
            return true
        }
    }

    class SelectionItemCallback : DiffUtil.ItemCallback<Selection>() {
        override fun areItemsTheSame(oldItem: Selection, newItem: Selection): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Selection, newItem: Selection): Boolean {
            return oldItem.text == newItem.text
        }
    }
}