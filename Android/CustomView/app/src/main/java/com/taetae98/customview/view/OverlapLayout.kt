package com.taetae98.customview.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.get

class OverlapLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = 0
        var height = 0
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        for (i in 0 until childCount) {
            if (i == childCount - 1) {
                width += get(i).measuredWidth
                height += get(i).measuredHeight
            } else {
                width += get(i).measuredWidth / 2
                height += get(i).measuredHeight / 2
            }
        }

        val w = if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
            MeasureSpec.getSize(widthMeasureSpec)
        } else {
            width
        }

        val h = if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
            MeasureSpec.getSize(widthMeasureSpec)
        } else {
            height
        }

        setMeasuredDimension(w, h)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var left = 0
        var top = 0
        for (view in children) {
            view.layout(left, top, left + view.measuredWidth, top + view.measuredHeight)

            left += view.measuredWidth / 2
            top += view.measuredHeight / 2
        }
    }
}