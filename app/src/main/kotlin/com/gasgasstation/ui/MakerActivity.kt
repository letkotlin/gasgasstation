package com.gasgasstation.ui

import android.os.Bundle
import com.gasgasstation.R
import com.gasgasstation.base.view.BaseActivity
import kotlinx.android.synthetic.main.activity_makers.*


class MakerActivity : BaseActivity() {

    override fun inject() {

    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_makers
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ll_close.setOnClickListener { finish() }
    }

}