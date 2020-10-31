package com.minosai.typingdnahack.ui.tweet.newtweet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.minosai.typingdnahack.base.BaseViewModel
import com.minosai.typingdnahack.model.Resource
import com.minosai.typingdnahack.ui.tweet.data.TweetRepo
import com.minosai.typingdnahack.ui.tweet.model.Tweet
import com.minosai.typingdnahack.utils.events.Event
import com.minosai.typingdnahack.utils.events.EventManager
import com.minosai.typingdnahack.utils.events.NewTweetEvent
import com.minosai.typingdnahack.utils.toLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewTweetViewModel(
    private val tweetRepo: TweetRepo,
    private val eventManager: EventManager
) : BaseViewModel() {

    private val newTweetStatus = MutableLiveData<Resource<Tweet>>()
    val newTweet = newTweetStatus.toLiveData()

    fun postNewTweet(content: String, pattern: String) {
        newTweetStatus.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val resource = tweetRepo.postNewTweet(content, pattern)
            newTweetStatus.postValue(resource)
            if (resource is Resource.Success) {
                eventManager.sendEvent(Event(NewTweetEvent))
                finishActivity()
            }
        }
    }

}