package com.gasgasstation.dagger

import com.gasgasstation.App
import com.gasgasstation.preference.SettingPreference
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by kws on 2017. 11. 29..
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

@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    fun provideApp() = app
}