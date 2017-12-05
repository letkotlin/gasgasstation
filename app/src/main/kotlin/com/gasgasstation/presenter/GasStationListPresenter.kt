package com.gasgasstation.presenter

/**
 * Created by kws on 2017. 11. 28..
 */
interface GasStationListPresenter {

    fun getCoord2address(x: Double, y: Double, inputCoord: String)

    fun getSettingData(key: String): Any?

    interface View {

        fun setCurrentAddress(address: String?)

        fun refresh()
    }

}