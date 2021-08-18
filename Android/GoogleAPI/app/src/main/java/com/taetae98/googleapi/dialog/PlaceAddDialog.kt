package com.taetae98.googleapi.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.taetae98.googleapi.R
import com.taetae98.googleapi.databinding.BindingBottomSheetDialog
import com.taetae98.googleapi.databinding.DialogPlaceAddBinding
import com.taetae98.googleapi.viewmodel.PlaceAddViewModel
import com.taetae98.googleapi.viewmodel.MapViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaceAddDialog : BindingBottomSheetDialog<DialogPlaceAddBinding>(R.layout.dialog_place_add) {
    private val viewModel by viewModels<PlaceAddViewModel>()
    private val mapViewModel by activityViewModels<MapViewModel>()

    private val args by navArgs<PlaceAddDialogArgs>()
    private val position by lazy { args.position }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateOnAdd()
        onCreatePosition()

        return binding.root
    }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.viewModel = viewModel
    }

    private fun onCreatePosition() {
        viewModel.latitude.value = position?.latitude?.toString() ?: ""
        viewModel.longitude.value = position?.longitude?.toString() ?: ""
    }

    private fun onCreateOnAdd() {
        binding.setOnAdd {
            mapViewModel.add(viewModel.toPlace())
            findNavController().navigateUp()
        }
    }
}