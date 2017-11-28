package com.gasgasstation.dagger

import com.gasgasstation.view.InitialSettingActivity
import dagger.Component

/**
 * Created by kws on 2017. 11. 23..
 */
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(
        InitialSettingModule::class)
)
interface InitialSettingComponent {
    fun inject(context: InitialSettingActivity)
}