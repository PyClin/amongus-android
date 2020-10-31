package com.minosai.typingdnahack.network

import com.minosai.typingdnahack.utils.Constants
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val AUTH_RETROFIT = "AUTH_RETROFIT"
const val FEATURE_RETROFIT = "FEATURE_RETROFIT"

val networkModule = module {

    factory { getNetworkInterceptor(get()) }

    factory(named(AUTH_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    factory(named(FEATURE_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

}