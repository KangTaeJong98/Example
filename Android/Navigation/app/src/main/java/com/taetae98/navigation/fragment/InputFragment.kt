package com.taetae98.navigation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.taetae98.navigation.R
import com.taetae98.navigation.base.BaseFragment
import com.taetae98.navigation.databinding.FragmentInputBinding
import com.taetae98.navigation.utility.DataBinding

class InputFragment : BaseFragment(), DataBinding<FragmentInputBinding> {
    override val binding: FragmentInputBinding by lazy { DataBinding.get(this, R.layout.fragment_input) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateViewDataBinding()
        onCreateOnAction()
        return binding.root
    }

    private fun onCreateViewDataBinding() {
        binding.lifecycleOwner = this
    }

    private fun onCreateOnAction() {
        binding.setOnAction {
            val args = binding.editText.editText!!.text.toString()
            findNavController().navigate(InputFragmentDirections.actionInputFragmentToOutputFragment(args))
        }
    }
}