package com.gasgasstation.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.gasgasstation.App
import com.gasgasstation.R
import com.gasgasstation.base.view.BaseActivity
import com.gasgasstation.dagger.InitSettingModule
import com.gasgasstation.model.enum.MapType
import com.gasgasstation.model.enum.OilType
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

    val oilAdapter by lazy { OilAdapter(oilData) }
    val navAdapter by lazy { NavAdapter(navData) }

    val oilData: ArrayList<String> = arrayListOf(OilType.B027.oil, OilType.D047.oil, OilType.B034.oil, OilType.C004.oil, OilType.K015.oil)
    val navData: ArrayList<String> = arrayListOf(MapType.GOOGLE.map, MapType.KAKAO.map, MapType.TMAP.map)

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

        rvOil.layoutManager = LinearLayoutManager(this)
        rvNavi.layoutManager = LinearLayoutManager(this)
//        oilAdapter = InitialSettingAdapter(oilData, { key, value ->
//
//        })
//        navAdapter = InitialSettingAdapter(navData, { key, value ->
//
//        })
        rvOil.adapter = oilAdapter
        rvNavi.adapter = navAdapter

        btNext.setOnClickListener {
            var intent = Intent(this, GasStationListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}