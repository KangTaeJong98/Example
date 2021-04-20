package com.taetae98.navigation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.taetae98.navigation.R
import com.taetae98.navigation.base.BaseFragment
import com.taetae98.navigation.databinding.FragmentUiBinding
import com.taetae98.navigation.utility.DataBinding

class UIFragment : BaseFragment(), DataBinding<FragmentUiBinding> {
    override val binding: FragmentUiBinding by lazy { DataBinding.get(this, R.layout.fragment_ui) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateViewDataBinding()
        onCreateBottomNavigationView()
        return binding.root
    }

    private fun onCreateViewDataBinding() {
        binding.lifecycleOwner = this
    }

    private fun onCreateBottomNavigationView() {
        val navController = (childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).findNavController()
        binding.bottomNavigation.setupWithNavController(navController)
    }
}