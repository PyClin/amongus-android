package com.minosai.typingdnahack.ui.auth.data

import com.minosai.typingdnahack.ui.auth.model.LoginRequest
import com.minosai.typingdnahack.ui.auth.model.LoginResponse
import com.minosai.typingdnahack.ui.auth.model.SignupRequest
import com.minosai.typingdnahack.ui.auth.model.SignupResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("auth/users/")
    suspend fun signup(@Body signupRequest: SignupRequest): Response<SignupResponse>

    @POST("auth/token/")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

}