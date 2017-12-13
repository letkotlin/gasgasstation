package com.gasgasstation.presenter

import com.gasgasstation.model.SortType

/**
 * Created by kws on 2017. 11. 28..
 */
interface GasStationListPresenter {

    fun getGasStationList(x: Double, y: Double, inputCoord: String, outputCoord: String)

    fun landingGoogleMap(x: Double, y: Double, inputCoord: String, outputCoord: String)

    fun getCoord2address(x: Double, y: Double, inputCoord: String)

    fun getSettingData(key: String): String

    fun sortList(sortType: SortType)

    interface View {

        fun openGoogleMap(x: Double, y: Double)

        fun setCurrentAddress(address: String?)

        fun refresh()
    }

}