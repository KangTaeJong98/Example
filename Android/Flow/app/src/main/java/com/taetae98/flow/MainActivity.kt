package com.taetae98.flow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.taetae98.flow.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MainActivity : AppCompatActivity(), DataBinding<ActivityMainBinding> {
    override val binding: ActivityMainBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_main) }

    private var last = 0
    private val data by lazy { flow { while (true) {
        delay(1000)
        emit(last++.toString())
    }
    } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                delay(1000L)
                withContext(Dispatchers.Main) {
                    binding.text.text = data.first()
                }
            }
        }
    }
}