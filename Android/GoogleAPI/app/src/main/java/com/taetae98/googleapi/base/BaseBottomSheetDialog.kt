package com.taetae98.googleapi.base

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.annotation.AnimRes
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.taetae98.googleapi.R

abstract class BaseBottomSheetDialog : BottomSheetDialogFragment(), BaseDialogFragment {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_GoogleAPI_DialogStyle)
    }
}