package com.taetae98.contentprovider.base

import android.graphics.drawable.Drawable
import androidx.annotation.AnimRes
import androidx.fragment.app.DialogFragment

abstract class BaseDialog : DialogFragment(), BaseDialogFragment {
    override fun setLayout(width: Int, height: Int) {
        dialog?.window?.setLayout(width, height)
    }

    override fun setBackground(drawable: Drawable?) {
        dialog?.window?.setBackgroundDrawable(drawable)
    }

    override fun setAnimation(@AnimRes resId: Int) {
        dialog?.window?.attributes?.windowAnimations = resId
    }

    override fun finish() {
        requireActivity().finish()
    }
}