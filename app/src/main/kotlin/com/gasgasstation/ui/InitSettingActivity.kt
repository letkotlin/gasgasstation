package com.gasgasstation.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.gasgasstation.App
import com.gasgasstation.R
import com.gasgasstation.base.view.BaseActivity
import com.gasgasstation.constant.Const
import com.gasgasstation.constant.PreferenceName
import com.gasgasstation.dagger.InitSettingModule
import com.gasgasstation.model.MapType
import com.gasgasstation.model.OilType
import com.gasgasstation.model.Setting
import com.gasgasstation.presenter.InitSettingPresenter
import com.gasgasstation.ui.adapter.NavAdapter
import com.gasgasstation.ui.adapter.NavAdapterView
import com.gasgasstation.ui.adapter.OilAdapter
import com.gasgasstation.ui.adapter.OilAdapterView
import kotlinx.android.synthetic.main.activity_init_setting.*
import javax.inject.Inject


class InitSettingActivity : BaseActivity(), InitSettingPresenter.View {

    @Inject lateinit internal var presenter: InitSettingPresenter
    @Inject lateinit var oilAdapterView: OilAdapterView
    @Inject lateinit var navAdapterView: NavAdapterView

    val oilAdapter by lazy { OilAdapter(oilData, {key, value -> presenter.choiceData(key, value) }) }
    val navAdapter by lazy { NavAdapter(navData, {key, value -> presenter.choiceData(key, value) }) }

    val oilData: ArrayList<Setting> = arrayListOf(Setting(OilType.B027.oil),
            Setting(OilType.D047.oil),
            Setting(OilType.B034.oil),
            Setting(OilType.C004.oil),
            Setting(OilType.K015.oil))
    val navData: ArrayList<Setting> = arrayListOf(Setting(MapType.GOOGLE.map),
            Setting(MapType.KAKAO.map),
            Setting(MapType.TMAP.map))

    override fun inject() {
        (applicationContext as App)
                .appComponent
                .initSettingComponent(InitSettingModule(this, oilAdapter, navAdapter))
                .inject(this)
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_init_setting
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rv_oil.layoutManager = LinearLayoutManager(this)
        rv_navi.layoutManager = LinearLayoutManager(this)
        rv_oil.adapter = oilAdapter
        rv_navi.adapter = navAdapter

        Log.i(Const.TAG, "InitSettingActivity OIL_TYPE = " + presenter.getSettingData(PreferenceName.OIL_TYPE))
        Log.i(Const.TAG, "InitSettingActivity MAP_TYPE = " + presenter.getSettingData(PreferenceName.MAP_TYPE))

        baseSetting()

        bt_next.setOnClickListener {
            var intent = Intent(this, GasStationListActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun baseSetting() {
//        presenter.saveSettingData(PreferenceName.DISTANCE_TYPE, DistanceType.D3.distance)
//        presenter.saveSettingData(PreferenceName.SORT_TYPE, SortType.PRICE.sortType)
    }

    override fun refresh() {

    }

}