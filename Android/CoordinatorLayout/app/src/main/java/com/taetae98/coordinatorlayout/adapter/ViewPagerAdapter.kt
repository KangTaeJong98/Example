package com.taetae98.coordinatorlayout.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.taetae98.coordinatorlayout.R
import com.taetae98.coordinatorlayout.fragment.FavoriteFragment
import com.taetae98.coordinatorlayout.fragment.ListFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    companion object {
        const val FAVORITE_INDEX = 0
        const val LIST_INDEX = 1
    }

    private val fragments = arrayOf({ FavoriteFragment() }, { ListFragment() })

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position].invoke()
    }

    fun getTabText(position: Int): String {
        return when(position) {
            FAVORITE_INDEX -> {
                "Favorite"
            }
            LIST_INDEX -> {
                "List"
            }
            else -> {
                throw IllegalStateException("No Fragment")
            }
        }
    }

    fun getTabIcon(position: Int): Int {
        return when(position) {
            FAVORITE_INDEX -> {
                R.drawable.ic_favorite
            }
            LIST_INDEX -> {
                R.drawable.ic_list
            }
            else -> {
                throw IllegalStateException("No Fragment")
            }
        }
    }
}