package com.gasgasstation.api

import com.gasgasstation.BuildConfig
import com.gasgasstation.constant.Const
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by kws on 2017. 11. 30..
 */
@Singleton
@Module
class ApiModule {

    @Provides
    internal fun daumApi(): DaumApi {
        return DaumApi(provideRetrofit(interceptor(), Const.DAUM_API_URL))
    }

    @Provides
    internal fun opinetApi(): OpinetApi {
        return OpinetApi(provideRetrofit(interceptor(), Const.OPINET_API_URL))
    }

    private fun interceptor(): Interceptor {
        return Interceptor {
            val builder: Request.Builder = it.request().newBuilder()
            builder.header("Authorization", "KakaoAK " + BuildConfig.DAUM_API_KEY)
            it.proceed(builder.build())
        }
    }

    private fun provideRetrofit(interceptor: Interceptor, url: String): Retrofit {

        val logger = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logger.level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
                .connectTimeout(Const.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Const.WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Const.READ_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(logger)
                .addInterceptor(interceptor)
                .build()
        return Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
    }
}