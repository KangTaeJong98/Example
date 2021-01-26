package com.taetae98.coordinatorlayout.fragment

import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.taetae98.coordinatorlayout.R
import com.taetae98.coordinatorlayout.adapter.ViewPagerAdapter
import com.taetae98.coordinatorlayout.base.BaseFragment
import com.taetae98.coordinatorlayout.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    override fun init() {
        super.init()
        initTabLayout()
        initViewPager()
    }

    private fun initTabLayout() {
        with(binding.tabLayout) {
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.icon?.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(resources.getColor(R.color.red, null), BlendModeCompat.SRC_ATOP)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    tab?.icon?.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(resources.getColor(R.color.white, null), BlendModeCompat.SRC_ATOP)

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            })
        }
    }

    private fun initViewPager() {
        val viewPagerAdapter = ViewPagerAdapter(this)

        with(binding.viewPager) {
            adapter = viewPagerAdapter
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = viewPagerAdapter.getTabText(position)
            tab.setIcon(viewPagerAdapter.getTabIcon(position))
        }.attach()
    }
}