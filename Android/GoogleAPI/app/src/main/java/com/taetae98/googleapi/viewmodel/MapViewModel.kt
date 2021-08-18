package com.taetae98.googleapi.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.Marker
import com.taetae98.googleapi.dto.Place
import com.taetae98.googleapi.manager.DataManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val defaultDataManager: DataManager
) : ViewModel() {
    private val markers by lazy { ArrayList<Marker>() }

    val places by lazy { stateHandle.getLiveData("PLACES", emptyList<Place>()) }

    fun clear() {
        val iterator = markers.iterator()
        while (iterator.hasNext()) {
            iterator.next().remove()
            iterator.remove()
        }
    }

    fun add(place: Place) {
        ArrayList<Place>().apply {
            addAll(places.value!!)
            add(place)
        }.also {
            places.value = it
            viewModelScope.launch {
                defaultDataManager.set(it)
            }
        }
    }

    fun delete(place: Place) {
        ArrayList<Place>().apply {
            addAll(
                places.value!!.filter {
                    it != place
                }
            )
        }.also {
            places.value = it
            viewModelScope.launch {
                defaultDataManager.set(it)
            }
        }
    }

    fun add(marker: Marker) {
        markers.add(marker)
    }

    init {
        viewModelScope.launch {
            places.value = defaultDataManager.get()
        }
    }
}