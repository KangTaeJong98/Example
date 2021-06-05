package com.taetae98.paging.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.taetae98.paging.R
import com.taetae98.paging.adapter.WeekDayWebToonAdapter
import com.taetae98.paging.base.BaseFragment
import com.taetae98.paging.databinding.FragmentMainBinding
import com.taetae98.paging.utility.DataBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseFragment(), DataBinding<FragmentMainBinding> {
    override val binding by lazy { DataBinding.get<FragmentMainBinding>(this, R.layout.fragment_main) }

    @Inject
    lateinit var weekDayWebToonAdapter: WeekDayWebToonAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        context ?: return binding.root

        onCreateViewDataBinding()
        onCreateSupportActionBar()
        onCreateViewPager()
        onCreateTabLayoutMediator()

        return binding.root
    }

    private fun onCreateViewDataBinding() {
        binding.lifecycleOwner = this
    }

    private fun onCreateSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun onCreateViewPager() {
        with(binding.viewPager) {
            adapter = weekDayWebToonAdapter
        }
    }

    private fun onCreateTabLayoutMediator() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = weekDayWebToonAdapter.currentList[position].korean
        }.attach()
    }
}