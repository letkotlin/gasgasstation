package com.gasgasstation

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 네비게이션 바 숨김.
        var decorView = window.decorView
        var uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        decorView.systemUiVisibility = uiOptions

        // 일정 시간 후에 화면 전환
        Handler().postDelayed({
            var intent = Intent(this, InitialSettingActivity::class.java)
            startActivity(intent)

        },3000)

        setContentView(R.layout.activity_main)
    }
}