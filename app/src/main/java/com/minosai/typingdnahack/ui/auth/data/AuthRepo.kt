package com.minosai.typingdnahack.ui.auth.data

import com.minosai.typingdnahack.model.Resource
import com.minosai.typingdnahack.model.getIfSuccess
import com.minosai.typingdnahack.network.safeApiCall
import com.minosai.typingdnahack.ui.auth.model.LoginRequest
import com.minosai.typingdnahack.ui.auth.model.LoginResponse
import com.minosai.typingdnahack.ui.auth.model.SignupRequest
import com.minosai.typingdnahack.ui.auth.model.SignupResponse
import com.minosai.typingdnahack.utils.preference.AppPreferences

class AuthRepo(
    private val authApiService: AuthApiService,
    private val appPreferences: AppPreferences
) {

    suspend fun login(userName: String, password: String): Resource<LoginResponse> =
        LoginRequest(userName, password).let { request ->
            safeApiCall { authApiService.login(request) }
        }.let { response ->
            response.getIfSuccess()?.let {
                appPreferences.accessToken = it.accessToken
                appPreferences.refreshToken = it.refreshToken
            }
            response
        }

    suspend fun signup(
        userName: String,
        password: String,
        email: String,
        pattern: String
    ): Resource<SignupResponse> =
        SignupRequest(userName, password, email, pattern).let { request ->
            safeApiCall { authApiService.signup(request) }
        }

}