package com.minosai.typingdnahack.ui.tweet.newtweet

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.minosai.typingdnahack.R
import com.minosai.typingdnahack.base.BaseActivity
import com.minosai.typingdnahack.base.BaseViewModel
import com.minosai.typingdnahack.model.Resource
import com.minosai.typingdnahack.utils.nonNull
import com.minosai.typingdnahack.utils.toast
import com.typingdna.typingdnarecorderandroid.TypingDNARecorderMobile
import kotlinx.android.synthetic.main.activity_new_tweet.*
import org.koin.android.viewmodel.ext.android.viewModel

class NewTweetActivity : BaseActivity() {

    private val newTweetViewModel: NewTweetViewModel by viewModel()

    private val tdna by lazy { TypingDNARecorderMobile(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_tweet)
        initTDNA()
        setupObservers(newTweetViewModel)
        setupClicks()
    }

    private fun initTDNA() {
        tdna.start()
        tdna.addTarget(R.id.newTweetContentInput)
    }

    private fun setupClicks() {
        newTweetActionButton.setOnClickListener {
            val tweetContent = newTweetContentInput.text?.toString().nonNull()
            val pattern = tdna.getTypingPattern(0, tweetContent.length, tweetContent, 0)
            newTweetViewModel.postNewTweet(tweetContent, pattern)
        }

         newTweetClose.setOnClickListener {
             finish()
         }
    }

    override fun setupObservers(viewModel: BaseViewModel) {
        super.setupObservers(viewModel)

        newTweetViewModel.newTweet.observe(this, Observer {
            when (it) {
                is Resource.Success -> {
                    newTweetContainer.isVisible = true
                    newTweetProgressBar.isVisible = false
                }
                is Resource.Loading -> {
                    newTweetContainer.isVisible = false
                    newTweetProgressBar.isVisible = true
                }
                is Resource.Error -> {
                    newTweetContainer.isVisible = true
                    newTweetProgressBar.isVisible = false
                    toast(it.message)
                }
            }
        })
    }

    override fun onResume() {
        tdna.start()
        super.onResume()
    }

    override fun onDestroy() {
        tdna.stop()
        super.onDestroy()
    }

    override fun onPause() {
        tdna.pause()
        super.onPause()
    }

}