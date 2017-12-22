package com.gasgasstation.presenter

/**
 * Created by kws on 2017. 11. 28..
 */
interface SplashPresenter {

    fun saveSettingData(key: String, value: String)

    fun getSettingData(key: String): String

    interface View {

    }

}