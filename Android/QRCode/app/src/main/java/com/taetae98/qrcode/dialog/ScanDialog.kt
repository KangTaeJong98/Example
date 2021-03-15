package com.taetae98.qrcode.dialog

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.google.zxing.integration.android.IntentIntegrator


class ScanDialog : DialogFragment() {

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        IntentIntegrator.forSupportFragment(this).initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val qrCode = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (qrCode != null) {
            if (qrCode.contents == null) {
//                Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                findNavController().previousBackStackEntry?.savedStateHandle?.set("QR CODE", qrCode.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

        findNavController().navigateUp()
    }
}