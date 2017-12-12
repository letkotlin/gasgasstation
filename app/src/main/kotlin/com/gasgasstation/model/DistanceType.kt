package com.gasgasstation.model

/**
 * Created by kws on 2017. 11. 22..
 */
enum class DistanceType(val distance: String) {
    D3("3km"), D5("5km"), D10("10km"), D20("20km");

    companion object {
        fun getDistance(distance: String): String =
                when (distance) {
                    D3.distance -> "3000"
                    D5.distance -> "5000"
                    D10.distance -> "10000"
                    D20.distance -> "20000"
                    else -> "3000"
                }
    }
}