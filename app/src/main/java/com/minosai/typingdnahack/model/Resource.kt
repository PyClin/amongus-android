package com.minosai.typingdnahack.model

sealed class Resource<out T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T) : Resource<T>(data)

    class Loading<T>(
        data: T? = null,
        var refreshing: Boolean = false
    ) : Resource<T>(data)

    class Error<T>(
        message: String,
        data: T? = null,
        var statusCode: Int = 0,
        val throwable: Throwable? = null
    ) : Resource<T>(data, message)

}

fun <T> Resource<T>.getIfSuccess(): T? = if (this is Resource.Success) this.data else null