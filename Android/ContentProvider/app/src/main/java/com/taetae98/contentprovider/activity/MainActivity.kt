package com.taetae98.contentprovider.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.ui.AppBarConfiguration
import com.taetae98.contentprovider.R
import com.taetae98.contentprovider.databinding.ActivityMainBinding
import com.taetae98.contentprovider.databinding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override val homeIds by lazy { setOf(R.id.findFragment) }
    override val appBarConfiguration by lazy { AppBarConfiguration(homeIds) }
}