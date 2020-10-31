package com.minosai.typingdnahack.ui.auth.ui.login

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.minosai.typingdnahack.R
import com.minosai.typingdnahack.base.BaseActivity
import com.minosai.typingdnahack.model.Resource
import com.minosai.typingdnahack.ui.tweet.home.MainActivity
import com.minosai.typingdnahack.ui.auth.ui.signup.SignupActivity
import com.minosai.typingdnahack.utils.nonNull
import com.minosai.typingdnahack.utils.toast
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupClicks()
        setupObservers()
    }

    private fun setupObservers() {
        loginViewModel.loginResource.observe(this, Observer {
            when (it) {
                is Resource.Success -> {
                    loginContainer.isVisible = true
                    loginProgressBar.isVisible = false
                    startActivity<MainActivity>(true)
                }
                is Resource.Loading -> {
                    loginContainer.isVisible = false
                    loginProgressBar.isVisible = true
                }
                is Resource.Error -> {
                    loginContainer.isVisible = true
                    loginProgressBar.isVisible = false
                    toast(it.message)
                }
            }
        })
    }

    private fun setupClicks() {

        loginActionButton.setOnClickListener {
            loginViewModel.login(
                loginInputUserName.editText?.text?.toString().nonNull(),
                loginInputPassword.editText?.text?.toString().nonNull()
            )
        }

        loginSignupButton.setOnClickListener {
            startActivity<SignupActivity>()
        }
    }
}