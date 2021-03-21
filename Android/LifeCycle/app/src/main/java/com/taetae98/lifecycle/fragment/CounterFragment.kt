package com.taetae98.lifecycle.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.taetae98.lifecycle.R
import com.taetae98.lifecycle.base.BaseFragment
import com.taetae98.lifecycle.databinding.FragmentCounterBinding
import com.taetae98.lifecycle.model.CounterViewModel
import com.taetae98.lifecycle.model.CounterViewModelFactory

class CounterFragment : BaseFragment<FragmentCounterBinding>(R.layout.fragment_counter) {
    private val viewModel by viewModels<CounterViewModel> { CounterViewModelFactory(0, this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.counterLiveData.observe(viewLifecycleOwner) {
            binding.count = it
        }
    }

    override fun init() {
        initSupportActionbar()
        initOnCount()
        initOnReset()
    }

    private fun initSupportActionbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun initOnCount() {
        binding.setOnCount {
            viewModel.increase()
        }
    }

    private fun initOnReset() {
        binding.setOnReset {
            viewModel.reset()
        }
    }
}