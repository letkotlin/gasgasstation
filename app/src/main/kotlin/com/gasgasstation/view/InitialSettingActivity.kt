package com.gasgasstation.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import com.gasgasstation.R
import com.gasgasstation.base.BaseActivity

class InitialSettingActivity : BaseActivity() {
    override fun getLayoutResId(): Int {
        return R.layout.activity_initial_setting
    }

    private lateinit var oilList: RecyclerView
    private lateinit var btNext: Button

    val oilData = arrayOf("휘발유", "경유", "고급휘발유", "실내등유", "자동차부탄")

    var navList: RecyclerView? = null
    val navData = arrayOf("구글지도", "카카오네비", "티맵")

    private lateinit var linearLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        oilList = findViewById(R.id.rvOil)
        linearLayoutManager = LinearLayoutManager(this)
        oilList.layoutManager = linearLayoutManager

        btNext = findViewById(R.id.btNext)
        btNext.setOnClickListener {
            var intent = Intent(this, GasStationListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}