package com.gasgasstation.model.opinet

/**
 * Created by kws on 2017. 12. 5..
 */
data class OPINET(var RESULT: RESULT)

data class RESULT(var OIL: List<GasStation>)

data class GasStation(val DISTANCE: Double,
               val PRICE: Int,
               val POLL_DIV_CD: String,
               val GIS_X_COOR: String,
               val OS_NM: String,
               val UNI_ID: String,
               val GIS_Y_COOR: String)