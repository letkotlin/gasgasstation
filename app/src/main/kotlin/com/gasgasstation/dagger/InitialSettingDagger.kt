package com.gasgasstation.dagger

import com.gasgasstation.presenter.InitialSettingPresenter
import com.gasgasstation.presenter.InitialSettingPresenterImpl
import com.gasgasstation.view.InitialSettingActivity
import com.gasgasstation.view.adapter.InitialSettingAdapter
import com.gasgasstation.view.adapter.InitialSettingAdapterModel
import com.gasgasstation.view.adapter.InitialSettingAdapterView
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * Created by kws on 2017. 11. 29..
 */
@Subcomponent(modules = arrayOf(InitialSettingModule::class))
interface InitialSettingComponent {
    fun inject(activity: InitialSettingActivity)
}

@Module
class InitialSettingModule(private val oilAdapter: InitialSettingAdapter) {
//                           private val oilAdapter: InitialSettingAdapter,
//                           private val navAdapter: InitialSettingAdapter) {

//    @Provides
//    internal fun initialSettingPresenter(presenter: InitialSettingPresenterImpl): InitialSettingPresenter {
//        return presenter
//    }
//
//    @Provides
//    internal fun view(): InitialSettingPresenter.View {
//        return view
//    }

//    @Named("oilAdapter")
    @Provides
    internal fun oilAdapterView(): InitialSettingAdapterView = oilAdapter
//
//    @Provides
//    @Named("navAdapter")
//    internal fun navAdapterView(): InitialSettingAdapterView = navAdapter

    @Provides
    internal fun oilAdapterModel(): InitialSettingAdapterModel = oilAdapter
//
//
//    @Provides
//    internal fun oilAdapterModel(): InitialSettingAdapterModel = oilAdapter


}