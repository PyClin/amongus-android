package com.minosai.typingdnahack.network

import android.content.Context
import android.net.ConnectivityManager
import com.minosai.typingdnahack.App
import com.minosai.typingdnahack.BuildConfig
import com.minosai.typingdnahack.model.Resource
import com.minosai.typingdnahack.utils.preference.AppPreferences
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import java.net.ProtocolException
import okhttp3.Response as OkHttpResponse
import retrofit2.Response as RetrofitResponse

suspend fun <T> safeApiCall(request: suspend () -> RetrofitResponse<T>): Resource<T> {
    return try {
        val response = request()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                Resource.Success(body)
//                if (body.status.equals("success", ignoreCase = true)) {
//                    Resource.Success(body.payload)
//                } else {
//                    Resource.Error(body.message.nonNull(), statusCode = response.code())
//                }
            } else {
                Resource.Error("Server response error", statusCode = response.code())
            }
        } else {
            Resource.Error(
                "${response.code()} ${response.message()}",
                statusCode = response.code()
            )
        }
    } catch (e: NoContentException) {
        Resource.Error("204 There is no content", statusCode = 204)
    } catch (e: Exception) {
        val errorMessage = e.message ?: e.toString()
        if (BuildConfig.DEBUG) {
            Resource.Error("Network called failed with message: $errorMessage")
        } else {
            Resource.Error("Check your internet connection!")
        }
    }
}

fun getNetworkInterceptor(prefs: AppPreferences): OkHttpClient {

    val httpClient = OkHttpClient.Builder()

    httpClient.addInterceptor { chain ->
        return@addInterceptor try {
            chain.proceed(chain.request())
        } catch (e: ProtocolException) {
            OkHttpResponse.Builder()
                .request(chain.request())
                .code(204)
                .message("Not signed in")
                .body("".toResponseBody("text/plain".toMediaType()))
                .protocol(Protocol.HTTP_1_1)
                .build()
        }
    }

    prefs.accessToken?.let { token ->
        httpClient.addInterceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()

            return@addInterceptor chain.proceed(request)
        }
    }

    if (BuildConfig.DEBUG) {
        httpClient.addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
    }

    return httpClient.build()
}

fun isNetworkAvailable(): Boolean {
    val cm = App.instance.getSystemService(
        Context.CONNECTIVITY_SERVICE
    ) as? ConnectivityManager
    val activeNetwork = cm?.activeNetworkInfo
    return activeNetwork != null && activeNetwork.isConnected
}