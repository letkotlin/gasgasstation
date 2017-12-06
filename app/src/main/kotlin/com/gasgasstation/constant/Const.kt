package com.gasgasstation.constant

import com.gasgasstation.BuildConfig

/**
 * Created by kws on 2017. 11. 23..
 */
class Const {
    companion object {
        const val TAG = "GAS"
        const val CONNECT_TIMEOUT: Long = 30
        const val WRITE_TIMEOUT: Long = 30
        const val READ_TIMEOUT: Long = 30
        const val DAUM_API_URL: String = "https://dapi.kakao.com/"
        const val OPINET_API_URL: String = "http://www.opinet.co.kr"

        const val SETTING_TYPE = "SETTING_TYPE"

        const val DAUM_API_KEY: String = BuildConfig.DAUM_API_KEY
        const val OPINET_API_KEY: String = BuildConfig.OPINET_API_KEY
    }
}