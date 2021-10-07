package com.taetae98.activitylifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.taetae98.activitylifecycle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.setOnActivity {

        }
        binding.setOnDialog {

        }
        binding.setOnNotification {

        }
        binding.setOnClear {

        }

        binding.log += "onCreate\n"
        Log.d("PASS", "onCreate")
    }

    override fun onStart() {
        super.onStart()
        binding.log += "onStart"
        Log.d("PASS", "onStart")
    }

    override fun onResume() {
        super.onResume()
        binding.log += "onResume"
        Log.d("PASS", "onResume")
    }

    override fun onPause() {
        super.onPause()
        binding.log += "onPause"
        Log.d("PASS", "onPause")
    }

    override fun onStop() {
        super.onStop()
        binding.log += "onStop"
        Log.d("PASS", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.log += "onDestroy"
        Log.d("PASS", "onDestory")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.log += "onSaveInstanceState(Bundle)"
        Log.d("PASS", "onSaveInstanceState(Bundle)")
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        binding.log += "onSaveInstanceState(Bundle, PersistableBundle)"
        Log.d("PASS", "onSaveInstanceState(Bundle, PersistableBundle)")
    }
}