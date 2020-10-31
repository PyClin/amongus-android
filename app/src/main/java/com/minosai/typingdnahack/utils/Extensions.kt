package com.minosai.typingdnahack.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

val Any.TAG
    get() = this.javaClass.simpleName

fun String?.nonNull() = this ?: ""

fun Context.toast(message: String?) {
    if (message.isNullOrBlank()) return
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(message: String) {
    context?.toast(message)
}

fun View.showSnackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun Fragment.showSnackbar(message: String) {
    view?.showSnackbar(message)
}

fun CoroutineScope.launchWithTryCatch(
    dispatcher: CoroutineDispatcher,
    block: suspend () -> Unit
) {
    launch(dispatcher) {
        try {
            block.invoke()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

@SuppressLint("ResourceAsColor")
@ColorInt
fun Context.getAttrColorResCompat(@AttrRes id: Int): Int {
    val resolvedAttr = TypedValue()
    this.theme.resolveAttribute(id, resolvedAttr, true)
    val colorRes = resolvedAttr.run { if (resourceId != 0) resourceId else data }
    return ResourceUtils.getColor(colorRes)
}

fun Context.shareText(text: String) {
    Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }.let { intent ->
        Intent.createChooser(intent, null)
            .also(::startActivity)
    }
}

fun <T> MutableLiveData<T>.toLiveData(): LiveData<T> = this

inline fun <reified T> Any?.castTo(): T? = if (this is T) this else null