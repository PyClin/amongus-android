package com.minosai.typingdnahack.ui.tweet.di

import com.minosai.typingdnahack.network.FEATURE_RETROFIT
import com.minosai.typingdnahack.ui.tweet.data.TweetApiService
import com.minosai.typingdnahack.ui.tweet.data.TweetRepo
import com.minosai.typingdnahack.ui.tweet.home.MainViewModel
import com.minosai.typingdnahack.ui.tweet.newtweet.NewTweetViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val tweetModule = module {

    factory { get<Retrofit>(named(FEATURE_RETROFIT)).create(TweetApiService::class.java) }

    factory { TweetRepo(get(), get()) }

    viewModel { MainViewModel(get(), get()) }

    viewModel { NewTweetViewModel(get(), get()) }

}