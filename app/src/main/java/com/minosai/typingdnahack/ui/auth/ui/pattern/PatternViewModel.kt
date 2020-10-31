package com.minosai.typingdnahack.ui.auth.ui.pattern

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.minosai.typingdnahack.base.BaseViewModel
import com.minosai.typingdnahack.model.Resource
import com.minosai.typingdnahack.ui.auth.data.AuthRepo
import com.minosai.typingdnahack.utils.toLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PatternViewModel(private val authRepo: AuthRepo) : BaseViewModel() {

    private var userName = ""
    private var password = ""
    private var email = ""
    private var pattern = ""

    private val signupState = MutableLiveData<Resource<Any>>()
    val signupResource = signupState.toLiveData()

    fun init(userName: String, password: String, email: String, pattern: String) {
        this.userName = userName
        this.password = password
        this.email = email
        this.pattern = pattern
    }

    fun signup(extraPattern: String) {
        signupState.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val combinedPattern = StringBuilder(pattern).append(";").append(extraPattern).toString()
            val resource = authRepo.signup(userName, password, email, combinedPattern)
            if (resource is Resource.Success) {
                login(userName, password)
            } else {
                signupState.postValue(resource)
            }
        }
    }

    private suspend fun login(userName: String, password: String) {
        signupState.postValue(authRepo.login(userName, password))
    }

}