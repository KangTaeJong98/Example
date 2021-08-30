package com.taetae98.intent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.taetae98.intent.databinding.ActivityNumberInputBinding
import com.taetae98.intent.databinding.ActivityTextInputBinding

class NumberInputActivity : AppCompatActivity() {
    private var _binding: ActivityNumberInputBinding? = null
    private val binding: ActivityNumberInputBinding
        get() {
            return _binding!!
        }

    private val input by lazy { MutableLiveData("") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_number_input)

        binding.lifecycleOwner = this
        binding.intent = intent.toString()
        binding.input = input
        binding.setOnButton {
            setResult(RESULT_OK, Intent().apply {
                putExtra(Intent.EXTRA_TEXT, input.value)
            })
            finish()
        }
    }
}