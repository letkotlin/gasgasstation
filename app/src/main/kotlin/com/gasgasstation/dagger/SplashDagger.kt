package com.gasgasstation.dagger

import com.gasgasstation.presenter.SplashPresenter
import com.gasgasstation.presenter.SplashPresenterImpl
import com.gasgasstation.ui.SplashActivity
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * Created by kws on 2017. 11. 29..
 */
@Subcomponent(modules = arrayOf(SplashModule::class))
interface SplashComponent {
    fun inject(activity: SplashActivity)
}

@Module
class SplashModule(private val view: SplashPresenter.View) {

    @Provides
    internal fun splashPresenter(presenter: SplashPresenterImpl): SplashPresenter = presenter

    @Provides
    internal fun view(): SplashPresenter.View = view

}