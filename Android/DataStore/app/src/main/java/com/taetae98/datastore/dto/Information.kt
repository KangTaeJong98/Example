package com.taetae98.datastore.dto

import com.google.gson.annotations.SerializedName

data class Information(
    @SerializedName("USER_ID")
    val id: String = "",
    @SerializedName("KNAME")
    val name: String = "",
    @SerializedName("MOBNO")
    val phone: String = "",
    @SerializedName("DEPT_NAME")
    val department: String = "",
    @SerializedName("USER_NO")
    val studentId: String = ""
)