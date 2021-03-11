package com.taetae98.http.dto

import com.google.gson.annotations.SerializedName

data class RequestResult(
    @SerializedName("Content-Type")
    val contentType: String,
    @SerializedName("Method")
    val method: String,
    @SerializedName("Local IP")
    val localIP: String,
    @SerializedName("Remote IP")
    val remoteIP: String,
    @SerializedName("URL")
    val url: String,
    @SerializedName("Parameter1")
    val parameter1: String,
    @SerializedName("Parameter2")
    val parameter2: String
)