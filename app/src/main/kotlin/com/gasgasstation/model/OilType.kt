package com.gasgasstation.model

/**
 * Created by kws on 2017. 11. 22..
 */
enum class OilType(val oil: String) {
    B027("휘발유"), D047("경유"), B034("고급휘발유"), C004("실내등유"), K015("자동차부탄");

    companion object {
        fun getOilType(oil: String): String =
                when (oil) {
                    B027.oil -> B027.name
                    D047.oil -> D047.name
                    B034.oil -> B034.name
                    C004.oil -> C004.name
                    K015.oil -> K015.name
                    else -> B027.name
                }

        fun getOilName(oil: String): String =
                when (oil) {
                    B027.name -> B027.oil
                    D047.name -> D047.oil
                    B034.name -> B034.oil
                    C004.name -> C004.oil
                    K015.name -> K015.oil
                    else -> B027.oil
                }
    }
}
