package com.gasgasstation.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.gasgasstation.R
import gasgasstation.view.InitialSettingActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 일정 시간 후에 화면 전환
        Handler().postDelayed({
            var intent = Intent(this, InitialSettingActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)

    }
}