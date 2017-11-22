package com.gasgasstation.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.gasgasstation.R
import com.gasgasstation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_initial_setting.*


class InitialSettingActivity : BaseActivity() {
    override fun getLayoutResId(): Int {
        return R.layout.activity_initial_setting
    }

//    val oilData:List<Enum<OilType>> = listOf(OilType.B027, OilType.D047, OilType.B034, OilType.C004, OilType.K015)
//    val navData:List<Enum<MapType>> = listOf(MapType.GOOGLE, MapType.KAKAO, MapType.TMAP)

    val oilData = listOf("1","2","3")
    val navData = listOf("1","2","3")


    private lateinit var oilLinearLayoutManager: RecyclerView.LayoutManager
    private lateinit var navLinearLayoutManager: RecyclerView.LayoutManager
    private lateinit var oilAdapter:RecyclerAdapter
    private lateinit var navAdapter:RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        oilLinearLayoutManager = LinearLayoutManager(this)
        navLinearLayoutManager = LinearLayoutManager(this)

        rvOil.layoutManager = oilLinearLayoutManager
        rvNavi.layoutManager = navLinearLayoutManager

//        oilAdapter = RecyclerAdapter(oilData.map { it.name })
//        navAdapter = RecyclerAdapter(navData.map { it.name })



        oilAdapter = RecyclerAdapter(oilData, { key, value ->

            Log.d("tag","msg")
        })

        navAdapter = RecyclerAdapter(navData, { key, value ->

            Log.d("tag","msg2")
        })



        rvOil.adapter = oilAdapter
        rvNavi.adapter = navAdapter

        btNext.setOnClickListener {
            var intent = Intent(this, GasStationListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}