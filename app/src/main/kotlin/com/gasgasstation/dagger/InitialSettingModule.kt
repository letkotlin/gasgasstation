package com.gasgasstation.dagger

import com.gasgasstation.view.InitialSettingActivity
import dagger.Module
import dagger.Provides

/**
 * Created by kws on 2017. 11. 23..
 */
@Module
class InitialSettingModule(private val initialSettingActivity: InitialSettingActivity) {

    @Provides
    fun provideInitialSettingActivity() = initialSettingActivity
}