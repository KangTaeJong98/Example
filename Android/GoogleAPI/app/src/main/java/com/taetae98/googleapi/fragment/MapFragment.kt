package com.taetae98.googleapi.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.taetae98.googleapi.R
import com.taetae98.googleapi.databinding.BindingFragment
import com.taetae98.googleapi.databinding.FragmentMapBinding
import com.taetae98.googleapi.dto.Place
import com.taetae98.googleapi.viewmodel.MapViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : BindingFragment<FragmentMapBinding>(R.layout.fragment_map) {
    private val viewModel by activityViewModels<MapViewModel>()
    private val map by lazy { childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment }

    private var selectedPosition: LatLng? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreatePlaceList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateMarkerClickListener()
        onCreateOnLocationAdd()
        onCreateMap()

        return binding.root
    }

    private fun onCreateMap() {
        map.getMapAsync {
            it.setOnCameraMoveListener {
                binding.floatingActionButton.hide()
            }
            it.setOnMapClickListener { position ->
                selectedPosition = position
                binding.floatingActionButton.show()
            }
        }
    }


    private fun onCreatePlaceList() {
        viewModel.places.observe(viewLifecycleOwner) { list ->
            viewModel.clear()
            list.forEach { place ->
                map.getMapAsync { map ->
                    map.addMarker(
                        MarkerOptions().apply {
                            title(place.name)
                            position(LatLng(place.latitude, place.longitude))
                        }
                    ).also { marker ->
                        marker?.let { marker ->
                            marker.tag = place
                            viewModel.add(marker)
                        }
                    }
                }
            }
        }
    }

    private fun onCreateMarkerClickListener() {
        map.getMapAsync {
            it.setOnInfoWindowClickListener { marker ->
                val tag = marker.tag
                if (tag is Place) {
                    findNavController().navigate(MapFragmentDirections.actionMapFragmentToInformationDialog(tag))
                }
            }
        }
    }

    private fun onCreateOnLocationAdd() {
        binding.setOnLocationAdd {
            findNavController().navigate(MapFragmentDirections.actionMapFragmentToLocationAddDialog(selectedPosition))
        }
    }
}