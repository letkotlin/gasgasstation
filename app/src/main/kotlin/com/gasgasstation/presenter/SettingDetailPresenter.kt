package com.gasgasstation.presenter

/**
 * Created by kws on 2017. 11. 28..
 */
interface SettingDetailPresenter {

    fun saveSettingData(key: String, value: String)

    fun getSettingData(key: String): Any?

    fun choiceData(key: String, name: String)

    interface View {
        fun refresh()
    }

}