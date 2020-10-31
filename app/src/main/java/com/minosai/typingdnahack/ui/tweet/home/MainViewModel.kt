package com.minosai.typingdnahack.ui.tweet.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.minosai.typingdnahack.base.BaseViewModel
import com.minosai.typingdnahack.model.Resource
import com.minosai.typingdnahack.ui.auth.ui.login.LoginActivity
import com.minosai.typingdnahack.ui.tweet.data.TweetRepo
import com.minosai.typingdnahack.ui.tweet.home.rv.TweetViewHolder
import com.minosai.typingdnahack.ui.tweet.model.Tweet
import com.minosai.typingdnahack.ui.tweet.newtweet.NewTweetActivity
import com.minosai.typingdnahack.utils.events.Event
import com.minosai.typingdnahack.utils.events.EventManager
import com.minosai.typingdnahack.utils.events.NewTweetEvent
import com.minosai.typingdnahack.utils.toLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(
    private val tweetRepo: TweetRepo,
    private val eventManager: EventManager
) : BaseViewModel(), TweetViewHolder.Interaction {

    private val dateFormat = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm::ss'Z'",
        Locale.getDefault()
    ).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    private val tweetList = MutableLiveData<Resource<List<Tweet>>>()
    val tweets = tweetList.toLiveData()

    private val updateAdapterPositionChannel = Channel<Int>()
    val updateAdapterPositionFlow = updateAdapterPositionChannel.consumeAsFlow()

    private val shareText = MutableLiveData<String?>()
    val shareTextLD = shareText.toLiveData()

    private val newTweetObserver = Observer<Event> {
        fetchTweets()
    }

    init {
        checkLoginAndFetchData()
        eventManager.addObserver(NewTweetEvent, newTweetObserver)
    }

    override fun onCleared() {
        eventManager.removeObserver(NewTweetEvent, newTweetObserver)
    }

    private fun checkLoginAndFetchData() {
        if (tweetRepo.isUserLoggedIn()) {
            fetchTweets()
        } else {
            openActivity(LoginActivity::class, true)
        }
    }

    fun fetchTweets() {
        apiCall(tweetList) {
            val resource = tweetRepo.fetchAllTweet()
            if (resource is Resource.Success) {
                return@apiCall Resource.Success(
                    resource.data?.asReversed()/*?.sortedWith(kotlin.Comparator { data1, data2 ->
                        dateFormat.parse(data1.createdAt.nonNull())
                            ?.compareTo(dateFormat.parse(data2.createdAt.nonNull())) ?: 0
                    })*/
                        ?: listOf()
                )
            }
            resource
        }
    }

    fun newTweetClicked() {
        openActivity(NewTweetActivity::class)
    }

    fun resetCache() = tweetRepo.resetCache()

    override fun onTweetFlagClicked(data: Tweet?, position: Int) {
        data ?: return
        val isFlagged = data.isFlagged.not()
        tweetList.postValue(Resource.Loading(tweetList.value?.data))
        viewModelScope.launch(Dispatchers.IO) {
            val resource = tweetRepo.flagTweet(data.id, isFlagged)
            if (resource is Resource.Success) {
                data.isFlagged = isFlagged
                updateAdapterPositionChannel.offer(position)
            } else if (resource is Resource.Error) {
                toast(resource.message)
            }
            tweetList.postValue(Resource.Success(tweetList.value?.data ?: listOf()))
        }
    }

    override fun onTweetLikeClicked(data: Tweet?, position: Int) {
        data ?: return
        data.isLiked = data.isLiked.not()
        updateAdapterPositionChannel.offer(position)
    }

    override fun onTweetShareClicked(data: Tweet?, position: Int) {
        data ?: return
        shareText.postValue(data.content)
    }
}