package com.minosai.typingdnahack.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.style.ImageSpan
import android.util.DisplayMetrics
import androidx.annotation.*
import androidx.core.graphics.ColorUtils
import kotlin.math.roundToInt

@SuppressLint("StaticFieldLeak")
object ResourceUtils {

    private lateinit var mContext: Context

    fun initialize(context: Context) {
        mContext = context
    }

    fun getString(id: Int): String {
        return mContext.resources.getString(id)
    }

    fun getDrawable(id: Int): Drawable {
        return mContext.resources.getDrawable(id, mContext.theme)
    }

    fun getDrawable(context: Context, id: Int): Drawable {
        return context.resources.getDrawable(id, context.theme)
    }

    fun getString(id: Int, number: Int): String {
        return mContext.resources.getString(id, number)
    }

    fun getString(id: Int, vararg formatArgs: Any): String {
        return mContext.resources.getString(id, *formatArgs)
    }

    fun getStringArray(id: Int): Array<String> {
        return mContext.resources.getStringArray(id)
    }

    fun getQuantityString(id: Int, quantity: Int, vararg formatArgs: Any): String {
        return mContext.resources.getQuantityString(id, quantity, *formatArgs)
    }

    fun getBoolean(id: Int): Boolean {
        return mContext.resources.getBoolean(id)
    }

    @ColorInt
    fun getColor(@ColorRes color: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mContext.resources.getColor(color, mContext.theme)
        } else {
            mContext.resources.getColor(color)
        }
    }

    fun getColorWithAlpha(color: Int, @FloatRange(from = 0.0, to = 1.0) alpha: Float): Int {
        return ColorUtils.setAlphaComponent(
            getColor(color = color),
            (alpha * 255f).toInt()
        )
    }

    fun getDimensionPixelOffset(@DimenRes dimen: Int): Int {
        return mContext.resources.getDimensionPixelOffset(dimen)
    }

    fun getDimensionPixelSize(@DimenRes dimen: Int): Int {
        return mContext.resources.getDimensionPixelSize(dimen)
    }

    fun getDisplayMetrics(): DisplayMetrics {
        return mContext.resources.displayMetrics
    }

    fun getDimension(@DimenRes dimen: Int): Float {
        return mContext.resources.getDimension(dimen)
    }

    fun getIdentifier(name: String, defType: String, defPackage: String): Int {
        return mContext.resources.getIdentifier(name, defType, defPackage)
    }

    fun getResource(): Resources {
        return mContext.resources
    }

    fun getAssetManager(): AssetManager {
        return mContext.assets
    }

    fun getImageSpan(bitmap: Bitmap): ImageSpan {
        return ImageSpan(mContext, bitmap, ImageSpan.ALIGN_BASELINE)
    }

    fun getPercentScreenHeight(@FloatRange(from = 0.0, to = 1.0) percentage: Float): Int {

        return (getDisplayMetrics().heightPixels * percentage).roundToInt()
    }

    fun getPercentScreenWidth(@FloatRange(from = 0.0, to = 1.0) percentage: Float): Int {
        return (getDisplayMetrics().widthPixels * percentage).roundToInt()
    }

    fun getText(id: Int): CharSequence {
        return mContext.resources.getText(id)
    }

    @ColorInt
    fun getAttrColorResCompat(themedContext: Context, @AttrRes id: Int): Int {
        return themedContext.getAttrColorResCompat(id)
    }
}