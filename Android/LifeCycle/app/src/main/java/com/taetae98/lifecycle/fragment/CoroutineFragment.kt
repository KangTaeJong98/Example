package com.taetae98.lifecycle.fragment

import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.lifecycle.whenResumed
import androidx.lifecycle.whenStarted
import androidx.navigation.fragment.findNavController
import com.taetae98.lifecycle.R
import com.taetae98.lifecycle.adapter.LogAdapter
import com.taetae98.lifecycle.base.BaseFragment
import com.taetae98.lifecycle.databinding.FragmentCoroutineBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class CoroutineFragment : BaseFragment<FragmentCoroutineBinding>(R.layout.fragment_coroutine) {
    private var count = 0
    private val logList by lazy { ArrayList<String>() }
    private val logAdapter by lazy { LogAdapter().apply { submitList(logList) } }

    override fun init() {
        initCoroutine()
        initSupportActionbar()
        initRecyclerView()
        initOnNavigate()
    }

    private fun initCoroutine() {
        viewLifecycleOwner.lifecycleScope.launch {
            whenCreated {
                writeLog("${SimpleDateFormat.getTimeInstance().format(System.currentTimeMillis())} launch - launchWhenCreated")
            }
            whenStarted {
                writeLog("${SimpleDateFormat.getTimeInstance().format(System.currentTimeMillis())} launch - launchWhenStarted")
            }
            whenResumed {
                writeLog("${SimpleDateFormat.getTimeInstance().format(System.currentTimeMillis())} launch - launchWhenResumed")
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            writeLog("${SimpleDateFormat.getTimeInstance().format(System.currentTimeMillis())} launch - launchWhenCreated(${count++})")
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            writeLog("${SimpleDateFormat.getTimeInstance().format(System.currentTimeMillis())} launch - launchWhenStarted(${count++})")
        }

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            while (true) {
                writeLog("${SimpleDateFormat.getTimeInstance().format(System.currentTimeMillis())} launch - launchWhenResumed(${count++})")
                delay(1000L)
            }
        }
    }

    private fun initSupportActionbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun initRecyclerView() {
        with(binding.recyclerView) {
            adapter = logAdapter
        }
    }

    private fun initOnNavigate() {
        binding.setOnNavigate {
            findNavController().navigate(CoroutineFragmentDirections.actionCoroutineFragmentToCoroutineNextFragment())
        }
    }

    private fun writeLog(log: String) {
        logList.add(log)
        logAdapter.notifyItemInserted(logList.lastIndex)
        binding.recyclerView.scrollToPosition(logList.lastIndex)
    }
}