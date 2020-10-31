package com.minosai.typingdnahack.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @Expose @SerializedName("status") val status: String?,
    @Expose @SerializedName("message") val message: String?,
    @Expose @SerializedName("payload") val payload: T?
)