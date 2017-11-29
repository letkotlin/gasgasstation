package com.gasgasstation.dagger

import com.gasgasstation.network.api.DaumApi
import com.gasgasstation.network.api.OpinetApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by kws on 2017. 11. 29..
 */
@Module
class ApiModule {

    @Provides
    fun provideUserApi(retrofit: Retrofit): OpinetApi = retrofit.create(OpinetApi::class.java)

    @Provides
    fun provideDaumApi(retrofit: Retrofit): DaumApi = retrofit.create(DaumApi::class.java)
}