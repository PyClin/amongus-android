package com.minosai.typingdnahack.ui.auth.ui.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.minosai.typingdnahack.R
import com.minosai.typingdnahack.base.BaseActivity
import com.minosai.typingdnahack.model.Resource
import com.minosai.typingdnahack.ui.tweet.home.MainActivity
import com.minosai.typingdnahack.ui.auth.ui.login.LoginActivity
import com.minosai.typingdnahack.ui.auth.ui.pattern.PatternActivity
import com.minosai.typingdnahack.utils.nonNull
import com.minosai.typingdnahack.utils.toast
import com.typingdna.typingdnarecorderandroid.TypingDNARecorderMobile
import kotlinx.android.synthetic.main.activity_signup.*
import org.koin.android.viewmodel.ext.android.viewModel

class SignupActivity : BaseActivity() {

    private val signupViewModel: SignupViewModel by viewModel()

    private val tdna by lazy { TypingDNARecorderMobile(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        initTDNA()
        setupClicks()
        setupObservers()
    }

    private fun setupObservers() {
        signupViewModel.signupResource.observe(this, Observer {
            when (it) {
                is Resource.Success -> {
                    signupContainer.isVisible = true
                    signupProgressBar.isVisible = false
                    startActivity<MainActivity>(true)
                }
                is Resource.Loading -> {
                    signupContainer.isVisible = false
                    signupProgressBar.isVisible = true
                }
                is Resource.Error -> {
                    signupContainer.isVisible = true
                    signupProgressBar.isVisible = false
                    toast(it.message)
                }
            }
        })
    }

    private fun initTDNA() {
        tdna.start()
        tdna.addTarget(
            intArrayOf(
                R.id.signupInputUsernameET,
                R.id.signupInputEmailET,
                R.id.signupInputPasswordET,
                R.id.signupInputBioET
            )
        )
    }

    private fun setupClicks() {
        signupActionButton.setOnClickListener {
            val combinedInput = getCombinedInput()
            val pattern = tdna.getTypingPattern(0, combinedInput.length, combinedInput, 0)
            Log.d("AMONG_US_TYPING_DNA", pattern)
            signup(pattern)
        }
//
//        signupLoginButton.setOnClickListener {
//            startActivity<LoginActivity>(true)
//        }

        signupBack.setOnClickListener {
            finish()
        }
    }

    private fun signup(pattern: String) {
        Intent(this, PatternActivity::class.java).apply {
            putExtra("username", signupInputUsername.editText?.text?.toString().nonNull())
            putExtra("password", signupInputPassword.editText?.text?.toString().nonNull())
            putExtra("email", signupInputEmail.editText?.text?.toString().nonNull())
            putExtra("pattern", pattern)
            startActivity(this)
        }
//        signupViewModel.signup(
//            signupInputUsername.editText?.text?.toString().nonNull(),
//            signupInputPassword.editText?.text?.toString().nonNull(),
//            signupInputEmail.editText?.text?.toString().nonNull(),
//            pattern
//        )
    }

    private fun getCombinedInput(): String {
        return StringBuilder()
            .append(signupInputUsername.editText?.text?.toString())
            .append(signupInputEmail.editText?.text?.toString())
            .append(signupInputPassword.editText?.text?.toString())
            .append(signupInputBio.editText?.text?.toString())
            .toString()
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