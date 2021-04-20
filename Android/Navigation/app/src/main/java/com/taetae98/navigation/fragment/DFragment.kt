package com.taetae98.navigation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.taetae98.navigation.R
import com.taetae98.navigation.base.BaseFragment
import com.taetae98.navigation.databinding.FragmentDBinding
import com.taetae98.navigation.utility.DataBinding

class DFragment : BaseFragment(), DataBinding<FragmentDBinding> {
    override val binding: FragmentDBinding by lazy { DataBinding.get(this, R.layout.fragment_d) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateVewDataBinding()
        onCreateOnNavigate()

        return binding.root
    }

    private fun onCreateVewDataBinding() {
        binding.lifecycleOwner = this
    }

    private fun onCreateOnNavigate() {
        binding.setOnNavigate {
            findNavController().navigateUp()
        }
    }
}