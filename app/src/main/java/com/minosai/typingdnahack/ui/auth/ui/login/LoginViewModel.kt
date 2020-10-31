package com.minosai.typingdnahack.ui.auth.ui.login

import androidx.lifecycle.MutableLiveData
import com.minosai.typingdnahack.base.BaseViewModel
import com.minosai.typingdnahack.model.Resource
import com.minosai.typingdnahack.ui.auth.data.AuthRepo
import com.minosai.typingdnahack.ui.auth.model.LoginResponse
import com.minosai.typingdnahack.utils.toLiveData

class LoginViewModel(private val authRepo: AuthRepo) : BaseViewModel() {

    private val loginState = MutableLiveData<Resource<LoginResponse>>()
    val loginResource = loginState.toLiveData()

    fun login(userName: String, password: String) {
        apiCall(loginState) {
            authRepo.login(userName, password)
        }
    }
}