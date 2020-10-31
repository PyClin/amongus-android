package com.minosai.typingdnahack.ui.tweet.data

import com.minosai.typingdnahack.network.safeApiCall
import com.minosai.typingdnahack.ui.tweet.model.FlagTweetRequest
import com.minosai.typingdnahack.ui.tweet.model.NewTweetRequest
import com.minosai.typingdnahack.utils.preference.AppPreferences

class TweetRepo(
    private val tweetApiService: TweetApiService,
    private val appPreferences: AppPreferences
) {

    suspend fun fetchAllTweet() = safeApiCall { tweetApiService.fetchAllTweet() }

    suspend fun postNewTweet(content: String, pattern: String) =
        NewTweetRequest(content, pattern).let { request ->
            safeApiCall { tweetApiService.postNewTweet(request) }
        }

    suspend fun flagTweet(tweetId: Int, isFlagged: Boolean) =
        FlagTweetRequest(isFlagged).let { request ->
            safeApiCall { tweetApiService.flagTweet(tweetId.toString(), request) }
        }

    fun isUserLoggedIn() = appPreferences.accessToken.isNullOrBlank().not()

    fun resetCache() = appPreferences.resetCache()

}