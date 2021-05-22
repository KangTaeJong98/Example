package com.taetae98.activityresultcontract

import android.Manifest
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.taetae98.activityresultcontract.databinding.ActivityMainBinding
import com.taetae98.activityresultcontract.utility.DataBinding

class MainActivity : AppCompatActivity(), DataBinding<ActivityMainBinding> {
    override val binding by lazy { DataBinding.get<ActivityMainBinding>(this, R.layout.activity_main) }

    private val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        Log.d("PASS", it.toString())
        if (it.resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, it.data!!.getStringExtra("data"), Toast.LENGTH_SHORT).show()
        }
    }

    private val permissionRequest = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        it.entries.forEach { entry ->
            Log.d("PASS", "${entry.key} : ${entry.value}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateViewDataBinding()

        permissionRequest.launch(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
            )
        )
    }

    private fun onCreateViewDataBinding() {
        binding.lifecycleOwner = this
        binding.setOnClick {
            activityResult.launch(Intent(this, InputActivity::class.java))
        }
    }
}