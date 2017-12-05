package com.gasgasstation.presenter

import com.gasgasstation.model.SortType

/**
 * Created by kws on 2017. 11. 28..
 */
interface GasStationListPresenter {

    fun getCoord2address(x: Double, y: Double, inputCoord: String)

    fun getSettingData(key: String): Any?

    fun sortList(sortType : SortType)

    interface View {

        fun setCurrentAddress(address: String?)

        fun refresh()
    }

}