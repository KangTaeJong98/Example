package com.taetae98.googleapi.activity

import androidx.navigation.ui.AppBarConfiguration
import com.taetae98.googleapi.R
import com.taetae98.googleapi.databinding.ActivityMainBinding
import com.taetae98.googleapi.databinding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override val homeIds by lazy { setOf(R.id.signInFragment, R.id.mapFragment) }
    override val appBarConfiguration by lazy { AppBarConfiguration(homeIds) }
}