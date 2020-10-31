package com.minosai.typingdnahack.utils.preference

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun customPrefs(context: Context, name: String): SharedPreferences =
    context.getSharedPreferences(name, Context.MODE_PRIVATE)

class PreferenceProperty<T>(
    private val sharedPreferences: SharedPreferences,
    private val key: String,
    private val defaultValue: T,
    private val getter: SharedPreferences.(String, T) -> T,
    private val setter: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor
) : ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>): T =
        sharedPreferences.getter(key, defaultValue)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) =
        sharedPreferences.edit()
            .setter(key, value)
            .apply()
}

fun SharedPreferences.int(
    key: String, defaultValue: Int = 0
): ReadWriteProperty<Any, Int> =
    PreferenceProperty(
        sharedPreferences = this,
        key = key,
        defaultValue = defaultValue,
        getter = SharedPreferences::getInt,
        setter = SharedPreferences.Editor::putInt
    )

fun SharedPreferences.long(
    key: String, defaultValue: Long = 0
): ReadWriteProperty<Any, Long> =
    PreferenceProperty(
        sharedPreferences = this,
        key = key,
        defaultValue = defaultValue,
        getter = SharedPreferences::getLong,
        setter = SharedPreferences.Editor::putLong
    )

fun SharedPreferences.boolean(
    key: String, defaultValue: Boolean = false
): ReadWriteProperty<Any, Boolean> =
    PreferenceProperty(
        sharedPreferences = this,
        key = key,
        defaultValue = defaultValue,
        getter = SharedPreferences::getBoolean,
        setter = SharedPreferences.Editor::putBoolean
    )

fun SharedPreferences.string(
    key: String, defaultValue: String? = null
): ReadWriteProperty<Any, String?> =
    PreferenceProperty(
        sharedPreferences = this,
        key = key,
        defaultValue = defaultValue,
        getter = SharedPreferences::getString,
        setter = SharedPreferences.Editor::putString
    )