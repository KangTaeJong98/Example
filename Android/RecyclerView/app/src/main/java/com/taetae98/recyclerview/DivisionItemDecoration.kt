package com.taetae98.recyclerview

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView

class DivisionItemDecoration(private val colorString: String, private val margin: Int) : RecyclerView.ItemDecoration() {
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        val paint = Paint().apply {
            color = Color.parseColor(colorString)
        }
        val height = 1.toDp()

        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            if (i != parent.childCount - 1) {

                c.drawRect(child.left.toFloat() + margin, child.bottom.toFloat() + margin, child.right.toFloat() - margin, child.bottom.toFloat() + height + margin, paint)
            }
        }
    }
}