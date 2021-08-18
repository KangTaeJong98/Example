package com.taetae98.googleapi.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.taetae98.googleapi.R
import com.taetae98.googleapi.databinding.BindingDialog
import com.taetae98.googleapi.databinding.DialogPlaceInformationBinding
import com.taetae98.googleapi.viewmodel.MapViewModel

class PlaceInformationDialog : BindingDialog<DialogPlaceInformationBinding>(R.layout.dialog_place_information) {
    private val viewModel by activityViewModels<MapViewModel>()
    private val args by navArgs<PlaceInformationDialogArgs>()
    private val place by lazy { args.place }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateOnDelete()

        return binding.root
    }

    private fun onCreateOnDelete() {
        binding.setOnDelete {
            viewModel.delete(place)
            findNavController().navigateUp()
        }
    }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.place = place
    }
}
