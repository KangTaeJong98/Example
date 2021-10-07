package com.taetae98.androidcomponent

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.taetae98.androidcomponent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_main) }
    private var connection: MyBindService.MyBindConnection? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.setOnBind {
            if (connection == null) {
                val conn = object : MyBindService.MyBindConnection() {
                    override fun onServiceConnected(name: ComponentName, binder: IBinder) {
                        super.onServiceConnected(name, binder)
                        connection = this
                        Toast.makeText(this@MainActivity, getValue().toString(), Toast.LENGTH_SHORT).show()
                    }

                    override fun onServiceDisconnected(name: ComponentName) {
                        super.onServiceDisconnected(name)
                        connection = null
                    }
                }

                bindService(Intent(this, MyBindService::class.java), conn, BIND_AUTO_CREATE)
            } else {
                Toast.makeText(this, connection!!.getValue().toString(), Toast.LENGTH_SHORT).show()
            }
        }
        binding.setOnOff {
            connection?.let { unbindService(it) }
            stopService(Intent(this, MyBackgroundService::class.java))
            stopService(Intent(this, MyForegroundService::class.java))
        }
        binding.setOnBackground {
            startService(Intent(this, MyBackgroundService::class.java))
            stopService(Intent(this, MyForegroundService::class.java))
        }
        binding.setOnForeground {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(Intent(this, MyForegroundService::class.java))
            } else {
                startService(Intent(this, MyForegroundService::class.java))
            }
            stopService(Intent(this, MyBackgroundService::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        val manager = getSystemService(PowerManager::class.java)
        if (!manager.isIgnoringBatteryOptimizations(packageName)) {
            AlertDialog.Builder(this)
                .setTitle("Request Permission")
                .setMessage("We need permission to running app")
                .setCancelable(false)
                .setPositiveButton("OK") { _, _ ->
                    Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS).also {
                        startActivity(it)
                    }
                }
                .show()
        }
    }
}