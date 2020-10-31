package com.minosai.typingdnahack.ui.tweet.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FlagTweetRequest(
    @Expose @SerializedName("flagged") val isFlagged: Boolean
)