package com.taetae98.customview.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.taetae98.customview.R
import kotlin.math.min

class ProgressView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private val progressBarBackgroundPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        color = Color.parseColor("#646F88")
        strokeWidth = 20 * resources.displayMetrics.density
    }
    private val progressBarPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        color = Color.parseColor("#61AFEF")
        strokeWidth = 20 * resources.displayMetrics.density
    }

    private val percentPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL_AND_STROKE
        textAlign = Paint.Align.CENTER
        textSize = 20 * resources.displayMetrics.density
        color = Color.parseColor("#000000")
    }

    var maxValue = 100
        set(value) {
            field = value
            invalidate()
        }

    var value = 0
        set(value) {
            field = value
            invalidate()
        }

    var textSize: Float
        get() {
            return percentPaint.textSize
        }

        set(value) {
            percentPaint.textSize = value
            invalidate()
        }

    var textColor: Int
        get() {
            return percentPaint.color
        }
        set(value) {
            percentPaint.color = value
            invalidate()
        }

    var progressBarBackgroundColor: Int
        get() {
            return progressBarBackgroundPaint.color
        }
        set(value) {
            progressBarBackgroundPaint.color = value
            invalidate()
        }

    var progressBarColor: Int
        get() {
            return progressBarPaint.color
        }
        set(value) {
            progressBarPaint.color = value
            invalidate()
        }

    var progressBarWidth: Float
        get() {
            return progressBarPaint.strokeWidth
        }
        set(value) {
            progressBarPaint.strokeWidth = value
            progressBarBackgroundPaint.strokeWidth = value
            invalidate()
        }

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.ProgressView, 0, 0).apply {
            maxValue = getInt(R.styleable.ProgressView_maxValue, maxValue)
            value = getInt(R.styleable.ProgressView_value, value)
            textSize = getDimension(R.styleable.ProgressView_textSize, textSize)
            textColor = getColor(R.styleable.ProgressView_textColor, textColor)
            progressBarBackgroundColor = getColor(R.styleable.ProgressView_progressBarBackgroundColor, progressBarBackgroundColor)
            progressBarColor = getColor(R.styleable.ProgressView_progressBarColor, progressBarColor)
            progressBarWidth = getDimension(R.styleable.ProgressView_progressBarWidth, progressBarWidth)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var w = MeasureSpec.getSize(widthMeasureSpec)
        var h = MeasureSpec.getSize(heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        when {
            widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST -> {
                w = (100 * resources.displayMetrics.density + 0.5).toInt()
                h = (100 * resources.displayMetrics.density + 0.5).toInt()
            }
            widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.AT_MOST -> {
                h = w
            }
            widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.EXACTLY -> {
                w = h
            }
        }

        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        val left = (paddingLeft + progressBarWidth/2)
        val top = (paddingTop + progressBarWidth/2)
        val right = (width - paddingLeft - paddingRight - progressBarWidth/2)
        val bottom = (height - paddingTop - paddingBottom - progressBarWidth/2)
        val x = (left + right)/2
        val y = (top + bottom)/2 + textSize/2

        drawProgressBarBackground(canvas, left, top, right, bottom)
        drawProgress(canvas, left, top, right, bottom, 360F * value / maxValue)
        drawPercent(canvas, x, y)
    }

    private fun drawProgressBarBackground(canvas: Canvas, left: Float, top: Float, right: Float, bottom: Float) {
        canvas.drawArc(left, top, right, bottom, -90F, 360F, false, progressBarBackgroundPaint)
    }

    private fun drawProgress(canvas: Canvas, left: Float, top: Float, right: Float, bottom: Float, angle: Float) {
        canvas.drawArc(left, top, right, bottom, -90F, angle, false, progressBarPaint)
    }

    private fun drawPercent(canvas: Canvas, x: Float, y: Float) {
        canvas.drawText("${value * 100 / maxValue}%", x, y, percentPaint)
    }
}