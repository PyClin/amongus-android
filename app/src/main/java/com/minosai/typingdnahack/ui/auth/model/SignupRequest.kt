package com.minosai.typingdnahack.ui.auth.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SignupRequest(
    @Expose @SerializedName("username") val userName: String?,
    @Expose @SerializedName("password") val password: String?,
    @Expose @SerializedName("email") val email: String?,
    @Expose @SerializedName("pattern") val pattern: String?
)