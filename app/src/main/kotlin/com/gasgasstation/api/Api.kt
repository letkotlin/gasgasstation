package com.gasgasstation.api

import com.gasgasstation.model.daum.DaumAddress
import io.reactivex.Flowable
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
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

    fun coord2address(x: Double, y: Double, inputCoord: String): Flowable<DaumAddress> {
        return daumApi.coord2address(x, y, inputCoord)
    }

    internal interface DaumApi {
        @GET("/v2/local/geo/coord2address.json")
        fun coord2address(@Query("x") x: Double,
                          @Query("y") y: Double,
                          @Query("input_coord") inputCoord: String):
                Flowable<DaumAddress>

    }

    internal interface OpinetApi {

    }
}