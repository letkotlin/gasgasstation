package com.gasgasstation.dagger

import android.content.Context
import android.content.SharedPreferences
import com.gasgasstation.constant.Const
import javax.inject.Inject

/**
 * Created by kws on 2017. 11. 22..
 */
class PreferenceUtil @Inject internal constructor(private val context: Context) {

    fun getString(key: String, defaultValue: String?): String {
        return context.getSharedPreferences(Const.TAG, Context.MODE_PRIVATE).getString(key, defaultValue)
    }

    fun put(key: String, value: Any?) {
        val editor: SharedPreferences.Editor = context.getSharedPreferences(Const.TAG, Context.MODE_PRIVATE).edit()
        when (value) {
            is Int -> editor.putInt(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Long -> editor.putLong(key, value)
            is Float -> editor.putFloat(key, value)
            else -> editor.putString(key, value?.toString())
        }

        editor.apply()
    }

    fun clear(key: String) {
        val editor: SharedPreferences.Editor = context.getSharedPreferences(key, Context.MODE_PRIVATE).edit()
        editor.clear()
        editor.apply()
    }

}