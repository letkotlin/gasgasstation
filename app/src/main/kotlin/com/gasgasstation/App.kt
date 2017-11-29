package com.gasgasstation

import android.app.Application
import android.content.Context
import com.gasgasstation.dagger.*

/**
 * Created by kws on 2017. 11. 22..
 */
class App : Application() {

    val singleton: AppComponent by lazy {
        DaggerAppComponent.builder()
                .networkModule(NetworkModule())
                .preferenceModule(PreferenceModule())
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        fun getAppComponent(context: Context): AppComponent {
            return (context.applicationContext as App).singleton
        }
    }
}