package com.taetae98.oauth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.taetae98.oauth.databinding.ActivityMainBinding
import com.taetae98.oauth.utility.DataBinding

class MainActivity : AppCompatActivity(), DataBinding<ActivityMainBinding> {
    override val binding by lazy { DataBinding.get<ActivityMainBinding>(this, R.layout.activity_main) }

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateDataBinding()
        onCreateSupportActionBar()
    }

    private fun onCreateDataBinding() {
        binding.lifecycleOwner = this
    }

    private fun onCreateSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun setSupportActionBar(toolbar: Toolbar?) {
        super.setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController)
    }
}