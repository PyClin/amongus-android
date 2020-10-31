package com.minosai.typingdnahack.ui.tweet.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewTweetRequest(
    @Expose @SerializedName("content") val content: String?,
    @Expose @SerializedName("pattern") val pattern: String?
)