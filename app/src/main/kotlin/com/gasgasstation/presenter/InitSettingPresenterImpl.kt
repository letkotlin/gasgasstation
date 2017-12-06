package com.gasgasstation.presenter

import com.gasgasstation.base.adapter.AdapterModel
import com.gasgasstation.base.adapter.AdapterView
import com.gasgasstation.constant.PreferenceName
import com.gasgasstation.dagger.PreferenceUtil
import com.gasgasstation.model.Setting
import com.gasgasstation.ui.adapter.NavAdapterModel
import com.gasgasstation.ui.adapter.NavAdapterView
import com.gasgasstation.ui.adapter.OilAdapterModel
import com.gasgasstation.ui.adapter.OilAdapterView
import javax.inject.Inject

/**
 * Created by kws on 2017. 11. 28..
 */
class InitSettingPresenterImpl @Inject internal constructor(private val view: InitSettingPresenter.View,
                                                            private val navAdapterModel: NavAdapterModel,
                                                            private val oilAdapterModel: OilAdapterModel,
                                                            private val navAdapterView: NavAdapterView,
                                                            private val oilAdapterView: OilAdapterView,
                                                            private val preference: PreferenceUtil) : InitSettingPresenter {
    override fun choiceData(key: String, name: String) {
        var adapterModel: AdapterModel<Setting>
        var adapterView: AdapterView
        if (key == PreferenceName.MAP_TYPE) {
            adapterModel = navAdapterModel
            adapterView = navAdapterView
        } else {
            adapterModel = oilAdapterModel
            adapterView = oilAdapterView
        }

        val size = adapterModel.size() - 1
        for (i in 0..size) {
            var item: Setting? = adapterModel.getItem(i)
            adapterModel.getItem(i)!!.isChecked = item?.name.equals(name)
        }
        adapterView.refresh()

        saveSettingData(key, name)
    }

    override fun saveSettingData(key: String, value: String) {
        preference.put(key, value)
    }

    override fun getSettingData(key: String): String? = preference.getString(key, "")
}