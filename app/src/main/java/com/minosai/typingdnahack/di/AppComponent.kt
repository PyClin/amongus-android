package com.minosai.typingdnahack.di

import com.minosai.typingdnahack.network.networkModule
import com.minosai.typingdnahack.ui.auth.di.authModule
import com.minosai.typingdnahack.ui.tweet.di.tweetModule

val appComponent = listOf(
    appModule,
    networkModule,
    authModule,
    tweetModule
)