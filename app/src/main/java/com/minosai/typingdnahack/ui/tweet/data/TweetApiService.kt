package com.minosai.typingdnahack.ui.tweet.data

import com.minosai.typingdnahack.ui.tweet.model.FlagTweetRequest
import com.minosai.typingdnahack.ui.tweet.model.NewTweetRequest
import com.minosai.typingdnahack.ui.tweet.model.Tweet
import retrofit2.Response
import retrofit2.http.*

interface TweetApiService {

    @GET("api/tweet/")
    suspend fun fetchAllTweet(): Response<List<Tweet>>

    @POST("api/tweet/")
    suspend fun postNewTweet(@Body newTweetRequest: NewTweetRequest): Response<Tweet>

    @PATCH("api/tweet/{id}")
    suspend fun flagTweet(@Path("id") id: String, @Body flagTweetRequest: FlagTweetRequest): Response<Any>

}