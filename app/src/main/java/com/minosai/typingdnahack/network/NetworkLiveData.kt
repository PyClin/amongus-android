package com.minosai.typingdnahack.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData
import com.minosai.typingdnahack.App

object NetworkLiveData : LiveData<Boolean>() {

    private val networkRequest by lazy {
        NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
    }

    private val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            postValue(true)
        }

        override fun onUnavailable() {
            postValue(false)
        }

        override fun onLost(network: Network) {
            postValue(false)
        }
    }

    override fun onActive() {
        super.onActive()
        getDetails()
        postValue(isNetworkAvailable())
    }

    private fun getDetails() {
        val connectivityManager = App.instance
            .getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager?.registerDefaultNetworkCallback(callback)
        } else {
            connectivityManager?.registerNetworkCallback(networkRequest, callback)
        }
    }

}