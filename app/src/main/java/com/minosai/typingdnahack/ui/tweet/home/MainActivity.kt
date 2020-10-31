package com.minosai.typingdnahack.ui.tweet.home

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.minosai.typingdnahack.R
import com.minosai.typingdnahack.base.BaseActivity
import com.minosai.typingdnahack.base.BaseViewModel
import com.minosai.typingdnahack.model.Resource
import com.minosai.typingdnahack.ui.auth.ui.login.LoginActivity
import com.minosai.typingdnahack.ui.tweet.home.rv.TweetAdapter
import com.minosai.typingdnahack.utils.toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private val tweetAdapter by lazy { TweetAdapter(mainViewModel) }

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupObservers(mainViewModel)
        setupRV()
        setupClicks()
    }

    private fun setupClicks() {
        tweetListSwipeRefresh.setOnRefreshListener {
            mainViewModel.fetchTweets()
        }

        tweetListNewTweetFAB.setOnClickListener {
            mainViewModel.newTweetClicked()
        }

        tweetListLogout.setOnClickListener {
            mainViewModel.resetCache()
            startActivity<LoginActivity>(true)
        }
    }

    private fun setupRV() = with(tweetListRV) {
        layoutManager = LinearLayoutManager(this@MainActivity)
        adapter = tweetAdapter
    }


    override fun setupObservers(viewModel: BaseViewModel) {
        super.setupObservers(viewModel)

        mainViewModel.tweets.observe(this, Observer {
            when (it) {
                is Resource.Success -> {
                    tweetListSwipeRefresh.isRefreshing = false
                    tweetAdapter.dataList = it.data ?: listOf()
                }
                is Resource.Loading -> {
                    tweetListSwipeRefresh.isRefreshing = true
                }
                is Resource.Error -> {
                    tweetListSwipeRefresh.isRefreshing = false
                    toast(it.message)
                }
            }
        })

        lifecycleScope.launch {
            mainViewModel.updateAdapterPositionFlow.collect {
                tweetAdapter.notifyItemChanged(it)
            }
        }

        mainViewModel.shareTextLD.observe(this, Observer { text ->
            if (text.isNullOrBlank()) return@Observer
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }.let {
                Intent.createChooser(it, null)
            }.let(::startActivity)
        })
    }
}