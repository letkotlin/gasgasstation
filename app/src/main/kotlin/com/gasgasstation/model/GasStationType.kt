package com.gasgasstation.model

/**
 * Created by kws on 2017. 11. 22..
 */
// 상표(SKE:SK에너지, GSC:GS칼텍스, HDO:현대오일뱅크, SOL:S-OIL, RTO:자영알뜰, RTX:고속도로알뜰, NHO:농협알뜰, ETC:자가상표, E1G: E1, SKG:SK가스
enum class GasStationType(val gasStation: String) {
    ALL("전체"), SKE("SK에너지"), GSC("GS칼텍스"), HDO("현대오일뱅크"), SOL("S-OIL"), RTO("자영알뜰"), RTX("고속도로알뜰"), NHO("농협알뜰"), ETC("자가상표"), E1G("E1"), SKG("SK가스")
}