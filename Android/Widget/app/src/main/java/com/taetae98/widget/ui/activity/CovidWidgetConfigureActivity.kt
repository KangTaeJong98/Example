package com.taetae98.widget.ui.activity

import android.os.Bundle
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.taetae98.modules.library.navigation.NavigationActivity
import com.taetae98.widget.R
import com.taetae98.widget.databinding.ActivityCovidWidgetConfigureBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CovidWidgetConfigureActivity : NavigationActivity<ActivityCovidWidgetConfigureBinding>(
    R.layout.activity_covid_widget_configure, R.id.nav_host
) {
    override val appBarConfiguration by lazy {
        AppBarConfiguration(setOf(R.id.covidWidgetConfigureFragment))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateToolbar()
    }

    private fun onCreateToolbar() {
        with(binding.toolbar) {
            setupWithNavController(navController, appBarConfiguration)
            setSupportActionBar(this)
        }
    }
}