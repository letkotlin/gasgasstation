package com.gasgasstation.dagger

import com.gasgasstation.AppModule
import com.gasgasstation.preference.SettingPreference
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by kws on 2017. 11. 22..
 */
@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        PreferenceModule::class,
        NetworkModule::class)
)
interface AppComponent {
    fun retrofit(): Retrofit
    fun setting(): SettingPreference
}