package com.gasgasstation.dagger

import com.gasgasstation.presenter.InitSettingPresenter
import com.gasgasstation.presenter.InitSettingPresenterImpl
import com.gasgasstation.ui.InitSettingActivity
import com.gasgasstation.ui.adapter.NavAdapter
import com.gasgasstation.ui.adapter.NavAdapterView
import com.gasgasstation.ui.adapter.OilAdapter
import com.gasgasstation.ui.adapter.OilAdapterView
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Named

/**
 * Created by kws on 2017. 11. 29..
 */
@Subcomponent(modules = arrayOf(InitSettingModule::class))
interface InitSettingComponent {
    fun inject(activity: InitSettingActivity)
}

@Module
class InitSettingModule(private val view: InitSettingPresenter.View,
                        private val oilAdapter: OilAdapter,
                        private val navAdapter: NavAdapter) {

    @Provides
    internal fun initSettingPresenter(presenter: InitSettingPresenterImpl): InitSettingPresenter = presenter

    @Provides
    internal fun view(): InitSettingPresenter.View = view

    @Provides
    @Named("oilAdapter")
    internal fun oilAdapterView(): OilAdapterView = oilAdapter

    @Provides
    @Named("navAdapter")
    internal fun navAdapterView(): NavAdapterView = navAdapter

    @Provides
    internal fun oilAdapterModel(): OilAdapterView = oilAdapter

    @Provides
    internal fun navAdapterModel(): NavAdapterView = navAdapter

}