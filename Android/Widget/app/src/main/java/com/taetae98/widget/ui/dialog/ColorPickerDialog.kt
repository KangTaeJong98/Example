package com.taetae98.widget.ui.dialog

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.taetae98.modules.library.navigation.NavigationDialogFragment
import com.taetae98.widget.R
import com.taetae98.widget.databinding.DialogColorPickerBinding
import com.taetae98.widget.util.Logger
import com.taetae98.widget.viewModel.ColorPickerViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ColorPickerDialog : NavigationDialogFragment<DialogColorPickerBinding>(R.layout.dialog_color_picker) {
    private val args by navArgs<ColorPickerDialogArgs>()

    private val colorViewModel by viewModels<ColorPickerViewModel> {
        ColorPickerViewModel.Factory(
            hiltFactory = hiltFactory,
            color = args.color,
            owner = this
        )
    }

    @Inject
    lateinit var hiltFactory: ColorPickerViewModel.HiltFactory

    override fun onBindingCreated() {
        super.onBindingCreated()
        onCreateOnColor()

        binding.colorViewModel = colorViewModel
    }

    private fun onCreateOnColor() {
        colorViewModel.onColor = {
            findNavController().previousBackStackEntry?.savedStateHandle?.set(args.key, it)
            findNavController().navigateUp()
        }
    }
}