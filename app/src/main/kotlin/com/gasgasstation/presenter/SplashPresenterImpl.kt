package com.gasgasstation.presenter

import com.gasgasstation.dagger.PreferenceUtil
import javax.inject.Inject

/**
 * Created by kws on 2017. 11. 28..
 */
class SplashPresenterImpl @Inject internal constructor(private val view: SplashPresenter.View,
                                                       private val preference: PreferenceUtil) : SplashPresenter {

    override fun saveSettingData(key: String, value: String) {
        preference.put(key, value)
    }

    override fun getSettingData(key: String): String = preference.getString(key, "")
}