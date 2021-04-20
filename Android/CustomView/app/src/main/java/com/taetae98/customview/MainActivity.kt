package com.taetae98.customview

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.taetae98.customview.databinding.ActivityMainBinding
import com.taetae98.customview.utility.DataBinding

class MainActivity : AppCompatActivity(), DataBinding<ActivityMainBinding> {
    override val binding: ActivityMainBinding by lazy { DataBinding.get(this, R.layout.activity_main) }

    val liveData = MutableLiveData("Hello")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateViewDataBinding()

        liveData.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun onCreateViewDataBinding() {
        binding.lifecycleOwner = this
        binding.activity = this
        binding.text = liveData
    }
}