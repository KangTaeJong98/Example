package com.taetae98.recyclerview

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int, private val orientation: Int = RecyclerView.VERTICAL, private val includeEdge: Boolean = true) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)

        when(orientation) {
            RecyclerView.VERTICAL -> {
                val column = position % spanCount
                if (includeEdge) {
                    outRect.left = spacing - column * spacing / spanCount
                    outRect.right = (column + 1) * spacing / spanCount
                    outRect.bottom = spacing
                    if (position < spanCount) {
                        outRect.top = spacing
                    }
                } else {
                    outRect.left = column * spacing / spanCount
                    outRect.right = spacing - (column + 1) * spacing / spanCount
                    if (position >= spanCount) {
                        outRect.top = spacing
                    }
                }
            }
            RecyclerView.HORIZONTAL -> {
                val row = position % spanCount
                if (includeEdge) {
                    outRect.top = spacing - row * spacing / spanCount
                    outRect.bottom = (row + 1) * spacing / spanCount
                    outRect.right = spacing
                    if (position < spanCount) {
                        outRect.left = spacing
                    }
                } else {
                    outRect.top = row * spacing / spanCount
                    outRect.bottom = spacing - (row + 1) * spacing / spanCount
                    if (position >= spanCount) {
                        outRect.left = spacing
                    }
                }
            }
        }

    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
    }
}