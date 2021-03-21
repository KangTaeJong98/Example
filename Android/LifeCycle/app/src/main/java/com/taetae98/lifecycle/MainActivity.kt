package com.taetae98.lifecycle

import android.os.SystemClock
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.taetae98.lifecycle.adapter.RecordAdapter
import com.taetae98.lifecycle.base.BaseActivity
import com.taetae98.lifecycle.data.Record
import com.taetae98.lifecycle.databinding.ActivityMainBinding
import com.taetae98.lifecycle.model.ChronometerViewModel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment).navController
    }

    private val appBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(
                R.id.chronometerFragment, R.id.counterFragment, R.id.coroutineFragment
            ),
            binding.drawerLayout
        )
    }

    override fun init() {
        super.init()
        initNavigationView()
    }

    private fun initNavigationView() {
        with(binding) {
            navigation.setupWithNavController(navController)
        }
    }

    override fun setSupportActionBar(toolbar: Toolbar?) {
        super.setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        with(binding) {
            if (drawerLayout.isDrawerOpen(navigation)) {
                drawerLayout.closeDrawer(navigation)
            } else {
                super.onBackPressed()
            }
        }
    }
}