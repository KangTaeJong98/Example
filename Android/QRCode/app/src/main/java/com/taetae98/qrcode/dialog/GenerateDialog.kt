package com.taetae98.qrcode.dialog

import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.taetae98.qrcode.R
import com.taetae98.qrcode.base.BaseDialog
import com.taetae98.qrcode.databinding.DialogGenerateBinding

class GenerateDialog : BaseDialog<DialogGenerateBinding>(R.layout.dialog_generate) {
    override fun onResume() {
        super.onResume()
        setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun init() {
        super.init()
        initOnGenerate()
    }

    private fun initOnGenerate() {
        binding.setOnGenerate {
            val qrCode = binding.qrCodeInputLayout.editText!!.text.toString()
            findNavController().previousBackStackEntry?.savedStateHandle?.set("QR CODE", qrCode)
            findNavController().navigateUp()
        }
    }
}