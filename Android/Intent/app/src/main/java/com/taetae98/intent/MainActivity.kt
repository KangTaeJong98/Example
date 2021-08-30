package com.taetae98.intent

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.taetae98.intent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() {
            return _binding!!
        }

    private val resultToGetString = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        with(it) {
            binding.textView.text = when(resultCode) {
                RESULT_OK -> {
                    "RESULT_OK(${data?.getStringExtra(Intent.EXTRA_TEXT)})"
                }
                RESULT_CANCELED -> {
                    "RESULT_CANCELED"
                }
                else -> {
                    ""
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        Intent(this, MainActivity::class.java)
        binding.setOnButton {
            Intent("com.taetae98.action.EDIT").apply {
                addCategory(Intent.CATEGORY_DEFAULT)
                type = "text/*"
            }.also {
                if (it.resolveActivity(packageManager) != null) {
                    resultToGetString.launch(it)
//                    resultToGetString.launch(Intent.createChooser(it, "Halo"))
                } else {
                    Toast.makeText(this, "There is no available application.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}