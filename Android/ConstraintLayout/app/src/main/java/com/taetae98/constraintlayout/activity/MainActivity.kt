package com.taetae98.constraintlayout.activity

import android.os.Bundle
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.taetae98.constraintlayout.R
import com.taetae98.constraintlayout.databinding.ActivityMainBinding
import com.taetae98.constraintlayout.databinding.BindingActivity

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override val appBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(
                R.id.constraintFragment, R.id.chainFragment, R.id.guideLineFragment, R.id.placeFragment, R.id.ratioFragment
            ), binding.drawer
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateSupportActionBar()
        onCreateNavigationView()
    }

    private fun onCreateSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun onCreateNavigationView() {
        binding.navigationView.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        with(binding) {
            if (drawer.isDrawerOpen(navigationView)) {
                drawer.closeDrawer(navigationView)
            } else {
                super.onBackPressed()
            }
        }
    }
}