package com.taetae98.recyclerview

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.taetae98.recyclerview.base.BaseActivity
import com.taetae98.recyclerview.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment).navController
    }

    private val appBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(
                    R.id.chatFragment, R.id.galleryFragment,
                    R.id.toDoFragment, R.id.selectionFragment, R.id.snapHelperFragment
            ),
            binding.drawer
        )
    }

    override fun init() {
        super.init()
        initSupportActionBar()
        initNavigationView()
    }

    private fun initSupportActionBar() {
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun initNavigationView() {
        with(binding) {
            navigation.setupWithNavController(navController)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        with(binding) {
            if (drawer.isDrawerOpen(navigation)) {
                drawer.closeDrawer(navigation)
            } else {
                super.onBackPressed()
            }
        }
    }
}