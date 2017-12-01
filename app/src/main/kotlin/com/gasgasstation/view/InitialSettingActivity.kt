package com.gasgasstation.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.gasgasstation.App
import com.gasgasstation.R
import com.gasgasstation.base.view.BaseActivity
import com.gasgasstation.dagger.InitialSettingModule
import com.gasgasstation.model.enum.MapType
import com.gasgasstation.model.enum.OilType
import com.gasgasstation.view.adapter.InitialSettingAdapter
import com.gasgasstation.view.adapter.InitialSettingAdapterView
import kotlinx.android.synthetic.main.activity_initial_setting.*
import javax.inject.Inject


class InitialSettingActivity : BaseActivity() {

    //    @Inject lateinit internal var initialSettingPresenter: InitialSettingPresenter
    @Inject lateinit var oilAdapterView: InitialSettingAdapterView
    //    @Inject lateinit var navAdapterView: InitialSettingAdapterView
//
    private lateinit var oilAdapter: InitialSettingAdapter
//    private lateinit var navAdapter: InitialSettingAdapter

    val oilData: ArrayList<String> = arrayListOf(OilType.B027.oil, OilType.D047.oil, OilType.B034.oil, OilType.C004.oil, OilType.K015.oil)
    val navData: ArrayList<String> = arrayListOf(MapType.GOOGLE.map, MapType.KAKAO.map, MapType.TMAP.map)

    override fun inject() {
//        DaggerInitialSettingComponent.builder()
//                .initialSettingModule(InitialSettingModule(this))
//                .build()
//                .inject(InitialSettingActivity::class)
        (applicationContext as App)
                .appComponent
                .initialSettingComponent(InitialSettingModule(oilAdapter))
                .inject(this)
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_initial_setting
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rvOil.layoutManager = LinearLayoutManager(this)
//        rvNavi.layoutManager = LinearLayoutManager(this)
//        oilAdapter = InitialSettingAdapter(oilData, { key, value ->
//
//        })
//        navAdapter = InitialSettingAdapter(navData, { key, value ->
//
//        })
        rvOil.adapter = oilAdapter
//        rvNavi.adapter = navAdapter
//
//        btNext.setOnClickListener {
//            var intent = Intent(this, GasStationListActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
    }
}