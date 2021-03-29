package com.taetae98.datastore.dto

import com.google.gson.annotations.SerializedName

data class InformationBody(
    @SerializedName("USER_ID")
    val id: String = ""
) {
    val fsp_action = "xDefaultAction"
    val fsp_ds_cmd = listOf(hashMapOf(
        "SQL_ID" to "mobile/common:USER_INFO_SQL_S01",
        "EXEC" to "Y",
        "EXEC_CNT" to 1
    ))
}