package com.gasgasstation.ui

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.gasgasstation.R
import com.gasgasstation.base.view.BaseActivity
import com.gasgasstation.ui.adapter.FindSettingAdapter
import kotlinx.android.synthetic.main.activity_setting.*


class SettingActivity : BaseActivity() {

    override fun inject() {

    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_setting
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val findSettings: ArrayList<String> = arrayListOf(getString(R.string.find_distance), getString(R.string.oil_type),
                getString(R.string.gas_station_type), getString(R.string.sort_type), getString(R.string.map_type))
        val findSettingAdapter = FindSettingAdapter(findSettings)
        rv_find_setting.layoutManager = LinearLayoutManager(this)
        rv_find_setting.adapter = findSettingAdapter
        rv_find_setting.addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager(this).orientation))
        findSettingAdapter.refresh()

        val etcs: ArrayList<String> = arrayListOf(getString(R.string.maker), getString(R.string.reporting), getString(R.string.review_write))
        val etcAdapter = FindSettingAdapter(etcs)
        rv_etc.layoutManager = LinearLayoutManager(this)
        rv_etc.adapter = etcAdapter
        rv_etc.addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager(this).orientation))
        etcAdapter.refresh()

        tv_close.setOnClickListener { finish() }
    }

}