package com.gasgasstation.dagger

import com.gasgasstation.presenter.GasStationListPresenter
import com.gasgasstation.presenter.GasStationListPresenterImpl
import com.gasgasstation.ui.GasStationListActivity
import com.gasgasstation.ui.adapter.GasStationAdapter
import com.gasgasstation.ui.adapter.GasStationAdapterModel
import com.gasgasstation.ui.adapter.GasStationAdapterView
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * Created by kws on 2017. 11. 29..
 */
@Subcomponent(modules = arrayOf(GasStationListModule::class))
interface GasStationListComponent {
    fun inject(activity: GasStationListActivity)
}

@Module
class GasStationListModule(private val view: GasStationListPresenter.View,
                           private val gasStationAdapter: GasStationAdapter) {

    @Provides
    internal fun gasStationListPresenter(presenter: GasStationListPresenterImpl): GasStationListPresenter = presenter

    @Provides
    internal fun view(): GasStationListPresenter.View = view

    @Provides
    internal fun adapterView(): GasStationAdapterView = gasStationAdapter

    @Provides
    internal fun adapterModel(): GasStationAdapterModel = gasStationAdapter
}
