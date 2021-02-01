package com.taetae98.lifecycle.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.taetae98.lifecycle.R
import com.taetae98.lifecycle.model.ChronometerViewModel
import java.text.SimpleDateFormat

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("frame")
    fun setFrame(layout: FrameLayout, state: ChronometerViewModel.State?) {
        val resetFrame = layout.findViewById<View>(R.id.reset_frame)
        val runFrame = layout.findViewById<View>(R.id.run_frame)
        val stopFrame = layout.findViewById<View>(R.id.stop_frame)

        when(state ?: ChronometerViewModel.State.RESET) {
            ChronometerViewModel.State.RESET -> {
                resetFrame.visibility = View.VISIBLE
                runFrame.visibility = View.GONE
                stopFrame.visibility = View.GONE
            }
            ChronometerViewModel.State.RUN -> {
                resetFrame.visibility = View.GONE
                runFrame.visibility = View.VISIBLE
                stopFrame.visibility = View.GONE
            }
            ChronometerViewModel.State.STOP -> {
                resetFrame.visibility = View.GONE
                runFrame.visibility = View.GONE
                stopFrame.visibility = View.VISIBLE
            }
            else -> {

            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    @JvmStatic
    @BindingAdapter("time")
    fun setTime(view: TextView, time: Long?) {
        view.text = SimpleDateFormat("mm:ss").format(time ?:0 )
    }
}