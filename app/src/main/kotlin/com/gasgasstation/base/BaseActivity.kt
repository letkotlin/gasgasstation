package com.gasgasstation.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity

/**
 * Created by kws on 2017. 11. 22..
 */
abstract class BaseActivity : AppCompatActivity() {
    @LayoutRes abstract fun getLayoutResId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
    }

}