package com.gasgasstation.ui

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.gasgasstation.R
import com.gasgasstation.base.view.BaseActivity
import com.gasgasstation.constant.Const
import com.gasgasstation.model.GasStationType
import com.gasgasstation.model.MapType
import com.gasgasstation.model.OilType
import com.gasgasstation.ui.adapter.SettingDetailAdapter
import kotlinx.android.synthetic.main.activity_setting_detail.*


class SettingDetailActivity : BaseActivity() {

    override fun inject() {

    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_setting_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var settingType = intent.getStringExtra(Const.SETTING_TYPE)
        tv_title.text = settingType

        var items: ArrayList<String> = ArrayList()
        when (settingType) {
            getString(R.string.find_distance) -> items = arrayListOf("3km", "5km", "10km")
            getString(R.string.oil_type) -> items = arrayListOf(GasStationType.ALL.gasStation, GasStationType.SKE.gasStation, GasStationType.GSC.gasStation,
                    GasStationType.HDO.gasStation, GasStationType.SOL.gasStation, GasStationType.RTO.gasStation,
                    GasStationType.RTX.gasStation, GasStationType.NHO.gasStation, GasStationType.ETC.gasStation,
                    GasStationType.E1G.gasStation, GasStationType.SKG.gasStation)
            getString(R.string.gas_station_type) -> items = arrayListOf(OilType.B027.oil, OilType.D047.oil, OilType.B034.oil, OilType.C004.oil, OilType.K015.oil)
            getString(R.string.sort_type) -> items = arrayListOf(getString(R.string.sort_distance), getString(R.string.sort_price))
            getString(R.string.map_type) -> items = arrayListOf(MapType.GOOGLE.map, MapType.KAKAO.map, MapType.TMAP.map)
        }
        val settingAdapter = SettingDetailAdapter(items, { key -> })

        rv_setting.layoutManager = LinearLayoutManager(this)
        rv_setting.adapter = settingAdapter
        rv_setting.addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager(this).orientation))
        settingAdapter.refresh()

        ll_close.setOnClickListener { finish() }
    }

}