package com.gasgasstation.api

import com.gasgasstation.model.opinet.OPINET
import io.reactivex.Flowable
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

/**
 * Created by kws on 2017. 11. 30..
 */
class OpinetApi @Inject internal constructor(retrofit: Retrofit) {

    private val opinetService: OpinetService

    init {
        opinetService = retrofit.create(OpinetService::class.java)
    }

    fun findAllGasStation(code: String, x: Double, y: Double, radius: String, sort: String, prodcd: String, out: String): Flowable<OPINET> {
        return opinetService.findAllGasStation(code, x, y, radius, sort, prodcd, out)
    }

    internal interface OpinetService {
        @GET("/api/aroundAll.do")
        fun findAllGasStation(@Query("code") code: String,
                              @Query("x") x: Double,
                              @Query("y") y: Double,
                              @Query("radius") radius: String,
                              @Query("sort") sort: String,
                              @Query("prodcd") prodcd: String,
                              @Query("out") out: String):
                Flowable<OPINET>

    }

}