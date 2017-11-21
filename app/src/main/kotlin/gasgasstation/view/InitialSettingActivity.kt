package com.gasgasstation.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.gasgasstation.R

class InitialSettingActivity : AppCompatActivity() {

    private lateinit var oilList: RecyclerView

    val oilData = arrayOf("휘발유", "경유", "고급휘발유", "실내등유", "자동차부탄")

    var navList: RecyclerView? = null
    val navData = arrayOf("구글지도", "카카오네비", "티맵")

    private lateinit var linearLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial_setting)

        oilList = findViewById(R.id.rvOil)
        linearLayoutManager = LinearLayoutManager(this)
        oilList.layoutManager = linearLayoutManager

    }
}