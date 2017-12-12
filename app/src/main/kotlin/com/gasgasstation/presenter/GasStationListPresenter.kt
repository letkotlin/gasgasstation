package com.gasgasstation.presenter

import com.gasgasstation.model.SortType

/**
 * Created by kws on 2017. 11. 28..
 */
interface GasStationListPresenter {
    fun transCoord(x: Double, y: Double, inputCoord: String, outputCoord: String)

    fun transCoord2(x: Double, y: Double, inputCoord: String, outputCoord: String)

    fun getCoord2address(x: Double, y: Double, inputCoord: String)

    fun getSettingData(key: String): String

    fun sortList(sortType: SortType)

    interface View {

        fun setCurrentAddress(address: String?)

        fun refresh()
    }

}