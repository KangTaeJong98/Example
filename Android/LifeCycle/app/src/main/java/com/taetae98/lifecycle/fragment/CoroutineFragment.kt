package com.taetae98.lifecycle.fragment

import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.lifecycle.whenResumed
import androidx.lifecycle.whenStarted
import androidx.navigation.fragment.findNavController
import com.taetae98.lifecycle.R
import com.taetae98.lifecycle.base.BaseFragment
import com.taetae98.lifecycle.databinding.FragmentCoroutineBinding
import com.taetae98.lifecycle.databinding.FragmentCounterBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CoroutineFragment : BaseFragment<FragmentCoroutineBinding>(R.layout.fragment_coroutine) {
    private var count = 0

    override fun init() {
        initCoroutine()
        initSupportActionbar()
        initOnNext()
    }

    private fun initCoroutine() {
        viewLifecycleOwner.lifecycleScope.launch {
            whenCreated {
                Log.d("PASS", "launch - launchWhenCreated ${count++}")
                delay(1000L)
            }
            Log.d("PASS", "Next whenCreated")
            whenStarted {
                Log.d("PASS", "launch - launchWhenStarted ${count++}")
                delay(1000L)
            }
            Log.d("PASS", "Next whenStarted")
            whenResumed {
                Log.d("PASS", "launch - launchWhenResumed ${count++}")
                delay(1000L)
            }
            Log.d("PASS", "Next whenResumed")
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            while (true) {
                Log.d("PASS", "launchWhenCreated ${count++}")
                delay(1000L)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            while (true) {
                Log.d("PASS", "launchWhenStarted ${count++}")
                delay(1000L)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            while (true) {
                Log.d("PASS", "launchWhenResumed ${count++}")
                delay(1000L)
            }
        }
    }

    private fun initSupportActionbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun initOnNext() {
        binding.setOnNext {
            findNavController().navigate(CoroutineFragmentDirections.actionCoroutineFragmentToCoroutineNextFragment())
        }
    }
}