package com.taetae98.intent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.taetae98.intent.databinding.ActivityMainBinding
import com.taetae98.intent.databinding.ActivityTextInputBinding

class TextInputActivity : AppCompatActivity() {
    private var _binding: ActivityTextInputBinding? = null
    private val binding: ActivityTextInputBinding
        get() {
            return _binding!!
        }

    private val input by lazy { MutableLiveData("") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_text_input)

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