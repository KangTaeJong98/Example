package com.taetae98.contentprovider.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.taetae98.contentprovider.R
import com.taetae98.contentprovider.adapter.SimpleDataAdapter
import com.taetae98.contentprovider.application.toDP
import com.taetae98.contentprovider.database.AppDatabase
import com.taetae98.contentprovider.databinding.BindingFragment
import com.taetae98.contentprovider.databinding.FragmentFindBinding
import com.taetae98.contentprovider.utility.SpacingItemDecoration
import com.taetae98.contentprovider.viewmodel.SimpleDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindFragment : BindingFragment<FragmentFindBinding>(R.layout.fragment_find) {
    private val viewModel by viewModels<SimpleDataViewModel>()
    private val simpleDataAdapter by lazy { SimpleDataAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreateSimpleDataList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateRecyclerView()
        onCreateOnDataAdd()

        return binding.root
    }

    private fun onCreateRecyclerView() {
        with(binding.recyclerView) {
            adapter = simpleDataAdapter
            addItemDecoration(SpacingItemDecoration(10.toDP()))
        }
    }

    private fun onCreateOnDataAdd() {
        binding.setOnDataAdd {
            findNavController().navigate(FindFragmentDirections.actionFindFragmentToDataAddFragment())
        }
    }

    private fun onCreateSimpleDataList() {
        viewModel.simpleDataLiveData.observe(viewLifecycleOwner) {
            simpleDataAdapter.submitList(it)
        }
    }
}