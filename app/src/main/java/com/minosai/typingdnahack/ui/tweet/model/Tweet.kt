package com.minosai.typingdnahack.ui.tweet.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Tweet(
    @Expose @SerializedName("id") val id: Int,
    @Expose @SerializedName("content") val content: String?,
    @Expose @SerializedName("flagged") var isFlagged: Boolean,
    @Expose @SerializedName("created_at") val createdAt: String?,
    @Expose @SerializedName("updated_at") val updatedAt: String?,
    var isLiked: Boolean = false
)