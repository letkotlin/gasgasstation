package com.gasgasstation.model

import com.gasgasstation.R

/**
 * Created by kws on 2017. 11. 22..
 */
// 상표(SKE:SK에너지, GSC:GS칼텍스, HDO:현대오일뱅크, SOL:S-OIL, RTO:자영알뜰, RTX:고속도로알뜰, NHO:농협알뜰, ETC:자가상표, E1G: E1, SKG:SK가스
enum class GasStationType(val gasStation: String) {
    ALL("전체"), SKE("SK에너지"), GSC("GS칼텍스"),
    HDO("현대오일뱅크"), SOL("S-OIL"), RTO("자영알뜰"),
    RTX("고속도로알뜰"), NHO("농협알뜰"), ETC("자가상표"),
    E1G("E1"), SKG("SK가스");

    companion object {
        fun getGasStationImg(gasStation: String): Int =
                when(gasStation) {
                    SKE.name -> R.drawable.ic_ske
                    GSC.name -> R.drawable.ic_gsc
                    HDO.name -> R.drawable.ic_hdo
                    SOL.name -> R.drawable.ic_sol
                    RTO.name -> R.drawable.ic_rtx
                    RTX.name -> R.drawable.ic_rtx
                    NHO.name -> R.drawable.ic_rtx
                    ETC.name -> R.drawable.ic_etc
                    E1G.name -> R.drawable.ic_e1g
                    SKG.name -> R.drawable.ic_skg
                    else -> R.drawable.ic_etc
                }

        fun getGasStation(gasStation: String): String =
                when(gasStation) {
                    SKE.gasStation -> SKE.name
                    GSC.gasStation -> GSC.name
                    HDO.gasStation -> HDO.name
                    SOL.gasStation -> SOL.name
                    RTO.gasStation -> RTO.name
                    RTX.gasStation -> RTX.name
                    NHO.gasStation -> NHO.name
                    ETC.gasStation -> ETC.name
                    E1G.gasStation -> E1G.name
                    SKG.gasStation -> SKG.name
                    else -> SKE.name
                }
    }

}