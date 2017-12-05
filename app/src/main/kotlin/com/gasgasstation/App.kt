package com.gasgasstation

import android.app.Application
import com.gasgasstation.api.ApiModule
import com.gasgasstation.dagger.*
import dagger.Component
import javax.inject.Singleton

/**
 * Created by kws on 2017. 11. 22..
 */
class App : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .settingModule(SettingModule(this))
                .build()

    }

    fun appComponent() = appComponent

}

@Component(modules = arrayOf(ApiModule::class, SettingModule::class))
@Singleton
interface AppComponent {
    fun inject(app: App)
    fun initSettingComponent(initSettingModule: InitSettingModule): InitSettingComponent
    fun gasStationListComponent(gasStationListModule: GasStationListModule): GasStationListComponent
}
