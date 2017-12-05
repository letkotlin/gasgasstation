package com.gasgasstation.ui

import android.os.Bundle
import com.gasgasstation.R
import com.gasgasstation.base.view.BaseActivity
import kotlinx.android.synthetic.main.activity_setting.*


class SettingActivity : BaseActivity() {

    override fun inject() {

    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_setting
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tv_close.setOnClickListener { finish() }
    }

}