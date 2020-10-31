package com.minosai.typingdnahack.di

import androidx.work.WorkManager
import com.minosai.typingdnahack.utils.Constants
import com.minosai.typingdnahack.utils.events.EventManager
import com.minosai.typingdnahack.utils.preference.AppPreferences
import com.minosai.typingdnahack.utils.preference.customPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single {
        customPrefs(
            androidContext(),
            Constants.PREFS
        )
    }

    single { AppPreferences(get()) }

    single { EventManager() }

    single { WorkManager.getInstance(androidContext()) }

}