package com.minosai.typingdnahack.ui.auth.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @Expose @SerializedName("refresh") val refreshToken: String?,
    @Expose @SerializedName("access") val accessToken: String?
)