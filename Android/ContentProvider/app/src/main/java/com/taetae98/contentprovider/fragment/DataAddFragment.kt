package com.taetae98.contentprovider.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.taetae98.contentprovider.R
import com.taetae98.contentprovider.databinding.BindingFragment
import com.taetae98.contentprovider.databinding.FragmentDataAddBinding
import com.taetae98.contentprovider.viewmodel.SimpleDataEditViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DataAddFragment : BindingFragment<FragmentDataAddBinding>(R.layout.fragment_data_add) {
    private val viewModel by viewModels<SimpleDataEditViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.viewModel = viewModel
    }

    private fun onCreateOnAdd() {
        binding.setOnAdd {
            viewModel.onCommit()
            findNavController().navigateUp()
        }
    }
}