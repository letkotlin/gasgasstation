package com.gasgasstation.dagger

import com.gasgasstation.view.InitialSettingActivity
import dagger.Component
import dagger.Module
import dagger.Provides

/**
 * Created by kws on 2017. 11. 29..
 */
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(
        InitialSettingModule::class)
)
interface InitialSettingComponent {
    fun inject(context: InitialSettingActivity)
}

@Module
class InitialSettingModule(private val initialSettingActivity: InitialSettingActivity) {

    @Provides
    fun provideInitialSettingActivity() = initialSettingActivity
}