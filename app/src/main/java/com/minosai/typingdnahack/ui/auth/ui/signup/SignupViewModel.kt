package com.minosai.typingdnahack.ui.auth.ui.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.minosai.typingdnahack.base.BaseViewModel
import com.minosai.typingdnahack.model.Resource
import com.minosai.typingdnahack.ui.auth.data.AuthRepo
import com.minosai.typingdnahack.utils.toLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupViewModel(private val authRepo: AuthRepo) : BaseViewModel() {

    private val signupState = MutableLiveData<Resource<Any>>()
    val signupResource = signupState.toLiveData()

    fun signup(userName: String, password: String, email: String, pattern: String) {
        signupState.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val resource = authRepo.signup(userName, password, email, pattern)
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