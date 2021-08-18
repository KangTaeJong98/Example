package com.taetae98.googleapi.dto

import com.google.firebase.database.PropertyName
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Place(
    var id: Long = 0L,
    @get:PropertyName("REFINE_WGS84_LOGT")
    @set:PropertyName("REFINE_WGS84_LOGT")
    var longitude: Double = 0.0,
    @get:PropertyName("REFINE_WGS84_LAT")
    @set:PropertyName("REFINE_WGS84_LAT")
    var latitude: Double = 0.0,
    @get:PropertyName("BIZPLC_NM")
    @set:PropertyName("BIZPLC_NM")
    var name: String = "",
    @get:PropertyName("REFINE_ROADNM_ADDR")
    @set:PropertyName("REFINE_ROADNM_ADDR")
    var address1: String = "",
    @get:PropertyName("REFINE_LOTNO_ADDR")
    @set:PropertyName("REFINE_LOTNO_ADDR")
    var address2: String = "",
    @get:PropertyName("CULTUR_PHSTRN_INDUTYPE_NM")
    @set:PropertyName("CULTUR_PHSTRN_INDUTYPE_NM")
    var description: String = "",
    @get:PropertyName("BSN_STATE_NM")
    @set:PropertyName("BSN_STATE_NM")
    var state: String = ""
) : Serializable