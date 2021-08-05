package com.taetae98.bridge.activity

import androidx.navigation.ui.AppBarConfiguration
import com.taetae98.bridge.R
import com.taetae98.bridge.databinding.ActivityMainBinding
import com.taetae98.bridge.databinding.BindingActivity

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override val appBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(R.id.webViewFragment)
        )
    }
}