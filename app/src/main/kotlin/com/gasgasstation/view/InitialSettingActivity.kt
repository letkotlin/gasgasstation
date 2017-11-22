package com.gasgasstation.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.gasgasstation.R
import com.gasgasstation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_initial_setting.*


class InitialSettingActivity : BaseActivity() {
    override fun getLayoutResId(): Int {
        return R.layout.activity_initial_setting
    }

    val oilData = arrayOf("휘발유", "경유", "고급휘발유", "실내등유", "자동차부탄")
    val navData = arrayOf("구글지도", "카카오네비", "티맵")

    private lateinit var linearLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        linearLayoutManager = LinearLayoutManager(this)
        rvOil.layoutManager = linearLayoutManager

        btNext.setOnClickListener {
            var intent = Intent(this, GasStationListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}