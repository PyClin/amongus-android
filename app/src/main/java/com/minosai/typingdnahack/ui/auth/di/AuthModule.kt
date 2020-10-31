package com.minosai.typingdnahack.ui.auth.di

import com.minosai.typingdnahack.network.AUTH_RETROFIT
import com.minosai.typingdnahack.ui.auth.data.AuthApiService
import com.minosai.typingdnahack.ui.auth.data.AuthRepo
import com.minosai.typingdnahack.ui.auth.ui.login.LoginViewModel
import com.minosai.typingdnahack.ui.auth.ui.pattern.PatternViewModel
import com.minosai.typingdnahack.ui.auth.ui.signup.SignupViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val authModule = module {

    factory { get<Retrofit>(named(AUTH_RETROFIT)).create(AuthApiService::class.java) }

    factory { AuthRepo(get(), get()) }

    viewModel { LoginViewModel(get()) }

    viewModel { SignupViewModel(get()) }

    viewModel { PatternViewModel(get()) }

}