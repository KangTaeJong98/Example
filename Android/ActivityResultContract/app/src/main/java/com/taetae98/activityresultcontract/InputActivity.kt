package com.taetae98.activityresultcontract

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.taetae98.activityresultcontract.databinding.ActivityInputBinding
import com.taetae98.activityresultcontract.utility.DataBinding

class InputActivity : AppCompatActivity(), DataBinding<ActivityInputBinding> {
    override val binding by lazy { DataBinding.get<ActivityInputBinding>(this, R.layout.activity_input) }

    private val viewModel by viewModels<InputViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateViewDataBinding()
    }

    private fun onCreateViewDataBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.setOnClick {
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra("data", viewModel.input.value)
            })
            finish()
        }
    }
}