package com.gasgasstation.preference

import android.content.Context

/**
 * Created by kws on 2017. 11. 22..
 */
class SettingPreference(context: Context) : PreferenceUtil(context) {
    private val PUSH_ENABLE = "push_enable"

    fun setPushEnable(enable: Boolean) {
        put(PUSH_ENABLE, enable)
    }

    fun isPushEnable(): Boolean {
        return get(PUSH_ENABLE) as Boolean
    }
}