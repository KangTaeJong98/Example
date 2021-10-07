package com.taetae98.customview.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children
import kotlin.math.min

class OverlapLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        )


        setMeasuredDimension(measuredWidth(widthMeasureSpec), measuredHeight(heightMeasureSpec))
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
    }
    private fun measureWidth(widthMeasureSpec: Int): Int {
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
            return MeasureSpec.getSize(widthMeasureSpec)
        }

        val width = children.reduceIndexed { index, total, view ->
            total + if (index == 0) {
                view.measuredWidth
            } else {
                view.measuredWidth/2
            }
        }

        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST) {
            return minOf(MeasureSpec.getSize(widthMeasureSpec), width)
        }

        return width
    }

    private fun measureHeight(heightMeasureSpec: Int): Int {
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            return MeasureSpec.getSize(heightMeasureSpec)
        }

        val height = children.reduceIndexed { index, total, view ->
            total + if (index == 0) {
                view.measuredHeight
            } else {
                view.measuredHeight/2
            }
        }

        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            return minOf(MeasureSpec.getSize(heightMeasureSpec), height)
        }

        return height
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