package com.gasgasstation.api

import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by kws on 2017. 11. 30..
 */
class Api @Inject internal constructor(retrofit: Retrofit) {

    private val daumApi: DaumApi
    private val opinetApi: OpinetApi

    init {
        daumApi = retrofit.create(DaumApi::class.java)
        opinetApi = retrofit.create(OpinetApi::class.java)
    }

    fun searchGas() : String{
        return "ok"
    }

    internal interface DaumApi {

    }

    internal interface OpinetApi {

    }
}