package com.gasgasstation.api

import com.gasgasstation.BuildConfig
import com.gasgasstation.constant.Const.Companion.CONNECT_TIMEOUT
import com.gasgasstation.constant.Const.Companion.DAUM_API_URL
import com.gasgasstation.constant.Const.Companion.READ_TIMEOUT
import com.gasgasstation.constant.Const.Companion.WRITE_TIMEOUT
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by kws on 2017. 11. 30..
 */
@Singleton
@Module
class ApiModule @Inject internal constructor() {

    @Provides
    internal fun gson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    internal fun okHttpClient(interceptor: Interceptor): OkHttpClient {
        val logger: HttpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logger.level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .cookieJar(JavaNetCookieJar(CookieManager(null, CookiePolicy.ACCEPT_ALL)))
                .addInterceptor(logger)
                .addInterceptor(interceptor)
                .build()
    }

    @Provides
    internal fun retrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(DAUM_API_URL)
                .client(okHttpClient)
                .build()
    }

    @Provides
    internal fun interceptor(): Interceptor {
        return Interceptor {
            val builder: Request.Builder = it.request().newBuilder()
            builder.header("Authorization", "KakaoAK " + BuildConfig.DAUM_API_KEY)

            it.proceed(builder.build())
        }
    }
}