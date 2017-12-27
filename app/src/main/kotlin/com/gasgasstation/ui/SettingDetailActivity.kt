package com.gasgasstation.ui

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.gasgasstation.App
import com.gasgasstation.R
import com.gasgasstation.base.view.BaseActivity
import com.gasgasstation.constant.Const
import com.gasgasstation.constant.PreferenceName
import com.gasgasstation.dagger.SettingDetailModule
import com.gasgasstation.model.*
import com.gasgasstation.presenter.SettingDetailPresenter
import com.gasgasstation.ui.adapter.SettingDetailAdapter
import com.gasgasstation.ui.adapter.SettingDetailAdapterModel
import com.gasgasstation.ui.adapter.SettingDetailAdapterView
import com.gasgasstation.util.RxBus
import kotlinx.android.synthetic.main.activity_setting_detail.*
import javax.inject.Inject


class SettingDetailActivity : BaseActivity(), SettingDetailPresenter.View {

    @Inject lateinit internal var presenter: SettingDetailPresenter
    @Inject lateinit var adapterView: SettingDetailAdapterView
    @Inject lateinit var adapterModel: SettingDetailAdapterModel

    lateinit var settingType: String
    var items: ArrayList<Setting> = ArrayList()
    val settingAdapter by lazy { SettingDetailAdapter(items, { name -> selectItem(name) }) }

    override fun inject() {
        (applicationContext as App)
                .appComponent
                .settingDetailComponent(SettingDetailModule(this, settingAdapter))
                .inject(this)
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_setting_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingType = intent.getStringExtra(Const.SETTING_TYPE)
        tv_title.text = settingType

        setBaseData()
        rv_setting.layoutManager = LinearLayoutManager(this)
        rv_setting.adapter = settingAdapter
        rv_setting.addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager(this).orientation))
        adapterModel.addItems(items)    //TODO 초기 Data 이후 아이템 추가 로직은 어디로 가야하나?
        refresh()

        ll_close.setOnClickListener { finish() }
    }

    private fun setBaseData() {
        when (settingType) {
            getString(R.string.find_distance) -> makeDistanceData()
            getString(R.string.oil_type) -> makeOilTypeData()
            getString(R.string.gas_station_type) -> makeGasStationTypeData()
            getString(R.string.sort_type) -> makeSortTypeData()
            getString(R.string.map_type) -> makeMapTypeData()
        }
    }

    private fun makeMapTypeData() {
        items = arrayListOf(Setting(MapType.GOOGLE.map),
                Setting(MapType.KAKAO.map),
                Setting(MapType.TMAP.map))
        fetchType(PreferenceName.MAP_TYPE, MapType.GOOGLE.map)
    }

    private fun makeSortTypeData() {
        items = arrayListOf(Setting(getString(R.string.sort_distance)),
                Setting(getString(R.string.sort_price)))
        fetchType(PreferenceName.SORT_TYPE, getString(R.string.sort_distance))
    }

    private fun makeOilTypeData() {
        items = arrayListOf(Setting(OilType.B027.oil),
                Setting(OilType.D047.oil),
                Setting(OilType.B034.oil),
                Setting(OilType.C004.oil),
                Setting(OilType.K015.oil))
        fetchType(PreferenceName.OIL_TYPE, OilType.B027.oil)
    }

    private fun makeDistanceData() {
        items = arrayListOf(Setting(DistanceType.D3.distance),
                Setting(DistanceType.D5.distance),
                Setting(DistanceType.D10.distance))
        fetchType(PreferenceName.DISTANCE_TYPE, DistanceType.D3.distance)
    }

    private fun makeGasStationTypeData() {
        items = arrayListOf(Setting(GasStationType.ALL.gasStation),
                Setting(GasStationType.SKE.gasStation),
                Setting(GasStationType.GSC.gasStation),
                Setting(GasStationType.HDO.gasStation),
                Setting(GasStationType.SOL.gasStation),
                Setting(GasStationType.RTO.gasStation),
                Setting(GasStationType.RTX.gasStation),
                Setting(GasStationType.ETC.gasStation),
                Setting(GasStationType.E1G.gasStation),
                Setting(GasStationType.SKG.gasStation))
        fetchType(PreferenceName.GAS_STATION_TYPE, GasStationType.ALL.gasStation)
    }

    private fun fetchType(preferenceName: String, default: String) {
        var type = presenter.getSettingData(preferenceName)
        if (type == null) {
            presenter.saveSettingData(preferenceName, default)
            type = default
        }
        for (setting: Setting in items) {
            if (setting.name == type) {
                setting.isChecked = true
                break
            }
        }
    }

    private fun selectItem(name: String) {
        Log.i(Const.TAG, "selectItem name = " + name)
        when (settingType) {
            getString(R.string.find_distance) -> {
                presenter.choiceData(PreferenceName.DISTANCE_TYPE, name)
                RxBus.publish(Const.BUS_GET_GAS_LIST, true)
            }
            getString(R.string.oil_type) -> {
                presenter.choiceData(PreferenceName.OIL_TYPE, name)
                RxBus.publish(Const.BUS_GET_GAS_LIST, true)
            }
            getString(R.string.gas_station_type) -> {
                presenter.choiceData(PreferenceName.GAS_STATION_TYPE, name)
                RxBus.publish(Const.BUS_GET_GAS_LIST, true)
            }
            getString(R.string.map_type) -> {
                presenter.choiceData(PreferenceName.MAP_TYPE, name)
                RxBus.publish(Const.BUS_GET_GAS_LIST, true)
            }
            getString(R.string.sort_type) -> {
                presenter.choiceData(PreferenceName.SORT_TYPE, name)
                RxBus.publish(Const.BUS_SORT_GAS_LIST, true)
            }
        }

    }

    override fun refresh() {
        adapterView.refresh()
    }


}