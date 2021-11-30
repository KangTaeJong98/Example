package com.taetae98.widget.ui.fragment

import android.Manifest
import android.app.Activity
import android.app.WallpaperManager
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.taetae98.modules.library.navigation.NavigationFragment
import com.taetae98.widget.R
import com.taetae98.widget.databinding.FragmentCovidWidgetConfigureBinding
import com.taetae98.widget.manager.CovidStatusManager
import com.taetae98.widget.repository.CovidWidgetRepository
import com.taetae98.widget.viewModel.CovidWidgetViewModel
import com.taetae98.widget.widget.CovidWidgetProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CovidWidgetConfigureFragment : NavigationFragment<FragmentCovidWidgetConfigureBinding>(R.layout.fragment_covid_widget_configure) {
    companion object {
        private const val TEXT_COLOR_KEY = "TEXT_COLOR_KEY"
        private const val BACKGROUND_COLOR_KEY = "BACKGROUND_COLOR_KEY"
    }

    private val args by lazy { CovidWidgetConfigureFragmentArgs.fromBundle(bundle = requireActivity().intent.extras ?: Bundle()) }
    private val appWidgetId by lazy { args.appWidgetId }

    private val covidWidgetViewModel by viewModels<CovidWidgetViewModel> {
        CovidWidgetViewModel.Factory(
            hiltFactory = hiltFactory,
            appWidgetId = appWidgetId,
            owner = this
        )
    }

    private val onWallPaperPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (requireContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            covidWidgetViewModel.wallpaper.postValue(WallpaperManager.getInstance(requireContext()).drawable)
        }
    }

    @Inject
    lateinit var hiltFactory: CovidWidgetViewModel.HiltFactory

    @Inject
    lateinit var covidStatusManager: CovidStatusManager

    @Inject
    lateinit var covidStatusRepository: CovidWidgetRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onObserveCovidStatusResponse()
        onObserveTextColor()
        onObserveBackgroundColor()
        onCreateWallPaper()
    }

    private fun onObserveTextColor() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Int>(TEXT_COLOR_KEY)?.observe(viewLifecycleOwner) {
            covidWidgetViewModel.textColor.postValue(it)
        }
    }

    private fun onObserveBackgroundColor() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Int>(BACKGROUND_COLOR_KEY)?.observe(viewLifecycleOwner) {
            covidWidgetViewModel.backgroundColor.postValue(it)
        }
    }

    private fun onObserveCovidStatusResponse() {
        covidWidgetViewModel.covidStatusResponse.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.autoComplete.setAdapter(
                    ArrayAdapter(
                        requireContext(), android.R.layout.simple_spinner_dropdown_item, it.body.cityList
                    )
                )
            }
        }
    }

    private fun onCreateWallPaper() {
        if (requireContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            covidWidgetViewModel.wallpaper.postValue(WallpaperManager.getInstance(requireContext()).drawable)
        } else {
            onWallPaperPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    override fun onBindingCreated() {
        super.onBindingCreated()
        binding.covidWidgetViewModel = covidWidgetViewModel.apply {
            onCovidWidget = callback@{
                val covidWidget = it.copy(id = appWidgetId)
                val covidStatusResponse = covidWidgetViewModel.covidStatusResponse.value ?: return@callback
                CoroutineScope(Dispatchers.IO).launch {
                    covidStatusRepository.insert(covidWidget)
                    AppWidgetManager.getInstance(requireContext()).updateAppWidget(
                        appWidgetId, CovidWidgetProvider.getRemoteViews(
                            context = requireContext(),
                            covidWidget = covidWidget,
                            covidStatusResponse = covidStatusResponse
                        )
                    )

                    setResult(Activity.RESULT_OK, Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId))
                    finish()
                }
            }
        }

        binding.setOnTextColor {
            findNavController().navigate(
                CovidWidgetConfigureFragmentDirections.actionCovidWidgetConfigureFragmentToColorPickerDialog(
                    key = TEXT_COLOR_KEY, color = covidWidgetViewModel.textColor.value!!
                )
            )
        }

        binding.setOnBackgroundColor {
            findNavController().navigate(
                CovidWidgetConfigureFragmentDirections.actionCovidWidgetConfigureFragmentToColorPickerDialog(
                    key = BACKGROUND_COLOR_KEY, color = covidWidgetViewModel.backgroundColor.value!!
                )
            )
        }

    }
}