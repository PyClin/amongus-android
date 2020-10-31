package com.minosai.typingdnahack.ui.auth.ui.pattern

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.minosai.typingdnahack.R
import com.minosai.typingdnahack.base.BaseActivity
import com.minosai.typingdnahack.model.Resource
import com.minosai.typingdnahack.ui.tweet.home.MainActivity
import com.minosai.typingdnahack.utils.nonNull
import com.minosai.typingdnahack.utils.toast
import com.typingdna.typingdnarecorderandroid.TypingDNARecorderMobile
import kotlinx.android.synthetic.main.activity_pattern.*
import org.koin.android.viewmodel.ext.android.viewModel

class PatternActivity : BaseActivity() {

    private val patternViewModel: PatternViewModel by viewModel()

    private val tdna by lazy { TypingDNARecorderMobile(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pattern)
        initTDNA()
        initVM()
        setupClicks()
        setupObservers()
    }

    private fun initTDNA() {
        tdna.start()
        tdna.addTarget(R.id.patternInput)
    }

    private fun initVM() = with(intent) {
        patternViewModel.init(
            getStringExtra("username").nonNull(),
            getStringExtra("password").nonNull(),
            getStringExtra("email").nonNull(),
            getStringExtra("pattern").nonNull()
        )
    }

    private fun setupClicks() {
        patternActionButton.setOnClickListener {
            val text = patternInput.text?.toString().nonNull()
            val pattern = tdna.getTypingPattern(0, text.length, text, 0)
            patternViewModel.signup(pattern)
        }

        patternBack.setOnClickListener {
            finish()
        }
    }

    private fun setupObservers() {
        patternViewModel.signupResource.observe(this, Observer {
            when (it) {
                is Resource.Success -> {
                    patternContainer.isVisible = true
                    patternProgressBar.isVisible = false
                    startActivity<MainActivity>(true)
                }
                is Resource.Loading -> {
                    patternContainer.isVisible = false
                    patternProgressBar.isVisible = true
                }
                is Resource.Error -> {
                    patternContainer.isVisible = true
                    patternProgressBar.isVisible = false
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