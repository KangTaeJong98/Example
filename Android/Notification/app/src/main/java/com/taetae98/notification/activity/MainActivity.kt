package com.taetae98.notification.activity

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.taetae98.notification.R
import com.taetae98.notification.base.BaseBindingActivity
import com.taetae98.notification.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override val appBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(
                R.id.normalFragment, R.id.actionFragment, R.id.extendFragment, R.id.groupFragment
            ), binding.drawerLayout
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateNavigationView()
    }

    private fun onCreateNavigationView() {
        binding.navigationView.setupWithNavController(navController)
    }

    override fun setSupportActionBar(toolbar: Toolbar?) {
        super.setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onBackPressed() {
        with(binding) {
            if (drawerLayout.isDrawerOpen(navigationView)) {
                drawerLayout.closeDrawer(navigationView)
            } else {
                super.onBackPressed()
            }
        }
    }
}