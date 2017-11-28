package com.gasgasstation

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by kws on 2017. 11. 22..
 */
@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    fun provideApp() = app
}