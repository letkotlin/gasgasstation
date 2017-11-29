package com.gasgasstation.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.gasgasstation.R
import com.gasgasstation.base.view.BaseActivity

class SplashActivity : BaseActivity() {
    override fun inject() {

    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_splash
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 일정 시간 후에 화면 전환
        Handler().postDelayed({
            var intent = Intent(this, InitialSettingActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)

    }

}