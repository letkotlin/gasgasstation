package com.gasgasstation.dagger

import com.gasgasstation.App
import com.gasgasstation.preference.SettingPreference
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by kws on 2017. 11. 29..
 */
@Singleton
@Module
class PreferenceModule {

    @Provides
    fun provideSettingPreference(app: App): SettingPreference {
        return SettingPreference(app)
    }
}