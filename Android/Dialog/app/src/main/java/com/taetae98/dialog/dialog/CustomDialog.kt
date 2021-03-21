package com.taetae98.dialog.dialog

import android.view.ViewGroup
import com.taetae98.dialog.R
import com.taetae98.dialog.base.BaseDialog
import com.taetae98.dialog.databinding.DialogCustomBinding

class CustomDialog : BaseDialog<DialogCustomBinding>(R.layout.dialog_custom) {
    override fun onResume() {
        super.onResume()
        setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun init() {

    }
}