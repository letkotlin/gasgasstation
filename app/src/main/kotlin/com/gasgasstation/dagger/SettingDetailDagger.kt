package com.gasgasstation.dagger

import com.gasgasstation.presenter.SettingDetailPresenter
import com.gasgasstation.presenter.SettingDetailPresenterImpl
import com.gasgasstation.ui.SettingDetailActivity
import com.gasgasstation.ui.adapter.SettingDetailAdapter
import com.gasgasstation.ui.adapter.SettingDetailAdapterModel
import com.gasgasstation.ui.adapter.SettingDetailAdapterView
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * Created by kws on 2017. 11. 29..
 */
@Subcomponent(modules = arrayOf(SettingDetailModule::class))
interface SettingDetailComponent {
    fun inject(activity: SettingDetailActivity)
}

@Module
class SettingDetailModule(private val view: SettingDetailPresenter.View,
                          private val adapter: SettingDetailAdapter) {

    @Provides
    internal fun settingDetailPresenter(presenter: SettingDetailPresenterImpl): SettingDetailPresenter = presenter

    @Provides
    internal fun view(): SettingDetailPresenter.View = view

    @Provides
    internal fun adapterView(): SettingDetailAdapterView = adapter

    @Provides
    internal fun adapterModel(): SettingDetailAdapterModel = adapter

}