package com.taetae98.googleapi.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.taetae98.googleapi.dto.Place
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaceAddViewModel @Inject constructor(
    stateHandle: SavedStateHandle
) : ViewModel() {
    val title by lazy { stateHandle.getLiveData("TITLE", "") }
    val longitude by lazy { stateHandle.getLiveData("LONGITUDE", "") }
    val latitude by lazy { stateHandle.getLiveData("LATITUDE", "") }
    val address1 by lazy { stateHandle.getLiveData("ADDRESS1", "") }
    val address2 by lazy { stateHandle.getLiveData("ADDRESS2", "") }
    val description by lazy { stateHandle.getLiveData("DESCRIPTION", "") }

    fun toPlace(): Place {
        return Place(
            name = title.value!!,
            longitude = longitude.value!!.toDouble(),
            latitude = latitude.value!!.toDouble(),
            address1 = address1.value!!,
            address2 = address2.value!!,
            description = description.value!!
        )
    }
}