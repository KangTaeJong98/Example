package com.taetae98.datastore.dto

import com.google.gson.annotations.SerializedName

data class SmartGachonMessage(
    @SerializedName("ErrorCode")
    val code: String = "",
    @SerializedName("ErrorMsg")
    val message: String = "",
    @SerializedName("ds_user_info")
    val information: List<Information> = emptyList()
)