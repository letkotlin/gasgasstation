package com.gasgasstation.presenter

import com.gasgasstation.dagger.PreferenceUtil
import com.gasgasstation.model.Setting
import com.gasgasstation.ui.adapter.SettingDetailAdapterModel
import com.gasgasstation.ui.adapter.SettingDetailAdapterView
import javax.inject.Inject

/**
 * Created by kws on 2017. 11. 28..
 */
class SettingDetailPresenterImpl @Inject internal constructor(private val view: SettingDetailPresenter.View, private val preference: PreferenceUtil,
                                                              private val adapterModel: SettingDetailAdapterModel,
                                                              private val adapterView: SettingDetailAdapterView) : SettingDetailPresenter {
    override fun choiceData(key: String, name: String) {
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

    override fun getSettingData(key: String): String = preference.getString(key, "")

}