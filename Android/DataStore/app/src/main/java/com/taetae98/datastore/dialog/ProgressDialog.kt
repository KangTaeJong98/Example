package com.taetae98.datastore.dialog

import com.taetae98.datastore.R
import com.taetae98.datastore.base.BaseDialog
import com.taetae98.datastore.databinding.DialogProgressBinding
import com.taetae98.datastore.toDp

class ProgressDialog : BaseDialog<DialogProgressBinding>(R.layout.dialog_progress) {
    override fun onResume() {
        super.onResume()
        setBackground(android.graphics.Color.TRANSPARENT)
        setLayout(100.toDp(), 100.toDp())
    }
}