package com.gasgasstation.presenter

/**
 * Created by kws on 2017. 11. 28..
 */
interface InitSettingPresenter {

    fun saveSettingData(key: String, value: String)

    fun getSettingData(key: String): String

    fun choiceData(key: String, name: String)

    interface View {
        fun refresh()
    }

}