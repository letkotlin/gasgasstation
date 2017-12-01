package com.gasgasstation

import android.app.Application
import com.gasgasstation.api.ApiModule
import com.gasgasstation.dagger.InitialSettingComponent
import com.gasgasstation.dagger.InitialSettingModule
import com.gasgasstation.dagger.PreferenceModule
import dagger.Component

/**
 * Created by kws on 2017. 11. 22..
 */
class App : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()

    }

    fun appComponent() = appComponent

}

@Component(modules = arrayOf(PreferenceModule::class, ApiModule::class))
interface AppComponent {
    fun inject(application: App)
    fun initialSettingComponent(initialSettingModule: InitialSettingModule): InitialSettingComponent
}
