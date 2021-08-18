package com.taetae98.contentprovider.base

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseHolder<E: Any>(view: View) : RecyclerView.ViewHolder(view) {
    val context: Context
        get() { return itemView.context }

    lateinit var element: E

    open fun onBind(element: E) {
        this.element = element
    }
    open fun onPayload(element: E, payload: MutableList<Any>) {

    }
}