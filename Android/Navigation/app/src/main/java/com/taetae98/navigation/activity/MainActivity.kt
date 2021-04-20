package com.taetae98.navigation.activity

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.taetae98.navigation.R
import com.taetae98.navigation.base.BaseActivity
import com.taetae98.navigation.databinding.ActivityMainBinding
import com.taetae98.navigation.utility.DataBinding

class MainActivity : BaseActivity(), DataBinding<ActivityMainBinding> {
    override val binding: ActivityMainBinding by lazy { DataBinding.get(this, R.layout.activity_main)}

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    private val appBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(
                R.id.AFragment, R.id.inputFragment
            ),
            binding.drawerLayout
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateSupportActionButton()
        onCreateNavigationView()
    }

    private fun onCreateSupportActionButton() {
        setSupportActionBar(binding.toolbar)
    }

    private fun onCreateNavigationView() {
        binding.navigation.setupWithNavController(navController)
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

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun setSupportActionBar(toolbar: Toolbar?) {
        super.setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}