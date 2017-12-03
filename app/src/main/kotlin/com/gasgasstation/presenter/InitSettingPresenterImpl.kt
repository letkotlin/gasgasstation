package com.gasgasstation.presenter

import android.util.Log
import com.gasgasstation.constant.Const
import com.gasgasstation.dagger.PreferenceUtil
import javax.inject.Inject

/**
 * Created by kws on 2017. 11. 28..
 */
class InitSettingPresenterImpl @Inject internal constructor(private val view: InitSettingPresenter.View, private val preference: PreferenceUtil) : InitSettingPresenter {

    override fun saveSettingData(key: String, value: String) {
        Log.i(Const.TAG, "InitSettingPresenterImpl saveSettingData() key = " + key + " value = " + value)
        preference.put(key, value)
    }

    override fun getSettingData(key: String): String? = preference.getString(key,"")
}