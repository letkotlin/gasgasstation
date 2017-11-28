package com.gasgasstation.preference

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by kws on 2017. 11. 22..
 */
abstract class PreferenceUtil constructor(private val context: Context) {

    fun get(key: String): Any? = context.getSharedPreferences(key, Context.MODE_PRIVATE)

    fun put(key: String, value: Any?): Unit {
        val editor: SharedPreferences.Editor = context.getSharedPreferences(key, Context.MODE_PRIVATE).edit()
        when (value) {
            is Int -> editor.putInt(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Long -> editor.putLong(key, value)
            is Float -> editor.putFloat(key, value)
            else -> editor.putString(key, value?.toString())
        }

        editor.apply()
    }

    fun clear(key: String): Unit {
        val editor: SharedPreferences.Editor = context.getSharedPreferences(key, Context.MODE_PRIVATE).edit()
        editor.clear()
        editor.apply()
    }

}