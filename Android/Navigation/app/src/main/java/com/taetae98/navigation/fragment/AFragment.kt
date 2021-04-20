package com.taetae98.navigation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.taetae98.navigation.R
import com.taetae98.navigation.base.BaseFragment
import com.taetae98.navigation.databinding.FragmentABinding
import com.taetae98.navigation.utility.DataBinding

class AFragment : BaseFragment(), DataBinding<FragmentABinding> {
    override val binding: FragmentABinding by lazy { DataBinding.get(this, R.layout.fragment_a) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateVewDataBinding()
        onCreateOnMove()

        return binding.root
    }

    private fun onCreateVewDataBinding() {
        binding.lifecycleOwner = this
    }

    private fun onCreateOnMove() {
        binding.setOnMove {
            findNavController().navigate(AFragmentDirections.actionAFragmentToBFragment())
        }
    }
}