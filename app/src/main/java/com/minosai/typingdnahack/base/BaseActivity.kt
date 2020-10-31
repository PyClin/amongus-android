package com.minosai.typingdnahack.base

import android.content.Intent
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.minosai.typingdnahack.utils.toast

open class BaseActivity : AppCompatActivity() {

    inline fun <reified T> startActivity(shouldFinish: Boolean = false) {
        startActivity(Intent(this, T::class.java))
        if (shouldFinish) {
            finish()
        }
    }

    @CallSuper
    protected open fun setupObservers(viewModel: BaseViewModel) {
        viewModel.toast.observe(this, Observer {
            toast(it)
        })

        viewModel.intent.observe(this, Observer {
            startActivity(Intent(this, it.java))
        })

        viewModel.finishActivity.observe(this, Observer {
            finish()
        })
    }

}