package com.taetae98.navigation.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.taetae98.navigation.R
import com.taetae98.navigation.base.BaseActivity
import com.taetae98.navigation.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    private val appBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(
                R.id.naverFragment, R.id.daumFragment
            ),
            binding.drawerLayout
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateNavigationView()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        with(binding) {
            if (drawerLayout.isDrawerOpen(navigation)) {
                drawerLayout.closeDrawer(navigation)
            } else {
                 navController.navigateUp()
            }
        }
    }

    private fun onCreateNavigationView() {
        binding.navigation.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun setSupportActionBar(toolbar: Toolbar?) {
        super.setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}