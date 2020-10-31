package com.minosai.typingdnahack.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minosai.typingdnahack.model.Resource
import com.minosai.typingdnahack.utils.LiveEvent
import com.minosai.typingdnahack.utils.nonNull
import com.minosai.typingdnahack.utils.toLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

open class BaseViewModel : ViewModel() {

    private val toastText = MutableLiveData<String>()
    val toast = toastText.toLiveData()

    private val intentActivity = LiveEvent<KClass<*>>()
    val intent = intentActivity.toLiveData()

    private val shouldFinishActivity = LiveEvent<Unit>()
    val finishActivity = shouldFinishActivity.toLiveData()

    protected fun toast(text: String?) {
        if (text.isNullOrBlank()) return
        toastText.postValue(text.nonNull())
    }

    protected fun openActivity(activityClass: KClass<*>, shouldFinish: Boolean = false) {
        intentActivity.postValue(activityClass)
        if (shouldFinish) finishActivity()
    }

    protected fun finishActivity() {
        shouldFinishActivity.postValue(Unit)
    }

    protected fun <T> apiCall(
        subject: MutableLiveData<Resource<T>>,
        block: suspend () -> Resource<T>
    ) {
        subject.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            subject.postValue(block())
        }
    }
}