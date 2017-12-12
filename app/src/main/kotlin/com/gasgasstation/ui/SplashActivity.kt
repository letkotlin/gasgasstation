package com.gasgasstation.ui

import android.Manifest
import android.content.Intent
import android.os.Bundle
import com.gasgasstation.R
import com.gasgasstation.base.view.BaseActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import io.reactivex.Flowable
import java.util.*
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity() {
    override fun inject() {

    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_splash
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showPermission()
        landingInitSetting()

    }

    fun showPermission() {
        TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage(getString(R.string.auth_rationale_msg))
                .setDeniedMessage(getString(R.string.auth_denied_msg))
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .check()
    }

    internal var permissionlistener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {

        }

        override fun onPermissionDenied(deniedPermissions: ArrayList<String>) {
            showPermission()
        }
    }

    fun landingInitSetting() {
        // 일정 시간 후에 화면 전환
        Flowable.timer(2, TimeUnit.SECONDS)
                .subscribe({
                    var intent = Intent(this, InitSettingActivity::class.java)
                    startActivity(intent)
                    finish()
                })
    }
}