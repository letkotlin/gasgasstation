package com.gasgasstation.api

import com.gasgasstation.model.daum.Coord2address
import com.gasgasstation.model.daum.TransCoord
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

    fun coord2address(x: Double, y: Double, inputCoord: String): Flowable<Coord2address> {
        return daumApi.coord2address(x, y, inputCoord)
    }

    fun tanscoord(x: Double, y: Double, inputCoord: String, outputCoord: String): Flowable<TransCoord> {
        return daumApi.tanscoord(x, y, inputCoord, outputCoord)
    }

    internal interface DaumApi {
        @GET("/v2/local/geo/coord2address.json")
        fun coord2address(@Query("x") x: Double,
                          @Query("y") y: Double,
                          @Query("input_coord") inputCoord: String):
                Flowable<Coord2address>

        @GET("v2/local/geo/transcoord.json")
        fun tanscoord(@Query("x") x: Double,
                      @Query("y") y: Double,
                      @Query("input_coord") inputCoord: String,
                      @Query("output_coord") outputCoord: String):
                Flowable<TransCoord>
    }

    internal interface OpinetApi {

    }
}