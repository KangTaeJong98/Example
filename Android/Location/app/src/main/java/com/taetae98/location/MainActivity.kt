package com.taetae98.location

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.view.Menu
import android.view.MenuItem
import com.taetae98.location.base.BaseActivity
import com.taetae98.location.databinding.ActivityMainBinding
import com.taetae98.location.service.LocationService
import com.taetae98.location.singleton.GPSManager


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    companion object {
        private const val PERMISSION_REQUEST_FOREGROUND_LOCATION = 1000
        private const val PERMISSION_REQUEST_BACKGROUND_LOCATION = 1001
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        grantResults.forEach {
            if (it == PackageManager.PERMISSION_DENIED) {
                return
            }
        }

        when(requestCode) {
            PERMISSION_REQUEST_FOREGROUND_LOCATION -> {
                initLocation()
            }
            PERMISSION_REQUEST_BACKGROUND_LOCATION -> {
                initBackgroundLocationService()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main_option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.refresh -> {
                initLocation()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun init() {
        super.init()
        initSupportActionBar()
        initForegroundLocationPermission()
        initLocation()
        initOnBackground()
    }

    private fun initSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun initForegroundLocationPermission(): Int {
        return checkSelfPermission(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_FOREGROUND_LOCATION)
    }

    private fun initBackgroundLocationPermission(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            checkSelfPermission(arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION), PERMISSION_REQUEST_BACKGROUND_LOCATION)
        } else {
            PackageManager.PERMISSION_GRANTED
        }
    }

    private fun checkSelfPermission(array: Array<String>, code: Int): Int {
        array.forEach {
            val deniedPermissions = ArrayList<String>()
            if (checkSelfPermission(it) == PackageManager.PERMISSION_DENIED) {
                deniedPermissions.add(it)
            }

            return if (deniedPermissions.isNotEmpty()) {
                requestPermissions(deniedPermissions.toTypedArray(), code)
                PackageManager.PERMISSION_DENIED
            } else {
                PackageManager.PERMISSION_GRANTED
            }
        }

        return PackageManager.PERMISSION_DENIED
    }

    private fun initLocation() {
        val manager = GPSManager.getInstance(this)
        val location = manager.getLocation()

        with(binding) {
            latitude = location.latitude
            longitude = location.longitude

            address = manager.getAddress()
        }
    }

    private fun initOnBackground() {
        binding.setOnInitBackground {
            if (initBackgroundLocationPermission() == PackageManager.PERMISSION_GRANTED) {
                initBackgroundLocationService()
            }
        }
    }

    private fun initBackgroundLocationService() {
        startService(Intent(this, LocationService::class.java))
    }
}