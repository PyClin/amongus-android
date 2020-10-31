package com.minosai.typingdnahack.ui.auth.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @Expose @SerializedName("username") val userName: String?,
    @Expose @SerializedName("password") val password: String?
)