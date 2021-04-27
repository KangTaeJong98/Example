package com.taetae98.customview

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import com.taetae98.customview.databinding.ActivityMainBinding
import com.taetae98.customview.utility.DataBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), DataBinding<ActivityMainBinding> {
    override val binding: ActivityMainBinding by lazy { DataBinding.get(this, R.layout.activity_main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateViewDataBinding()

        CoroutineScope(Dispatchers.Main).launch {
            while (binding.progress.value < binding.progress.maxValue) {
                delay(1000L)
                binding.progress.value += 1
            }
        }
    }

    private fun onCreateViewDataBinding() {
        binding.lifecycleOwner = this
    }
}