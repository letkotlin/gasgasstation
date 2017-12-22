package com.gasgasstation.ui

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.gasgasstation.App
import com.gasgasstation.R
import com.gasgasstation.base.view.BaseActivity
import com.gasgasstation.constant.Const
import com.gasgasstation.constant.PreferenceName
import com.gasgasstation.dagger.SplashModule
import com.gasgasstation.presenter.SplashPresenter
import com.kakao.util.helper.Utility.getKeyHash
import com.tedpark.tedpermission.rx2.TedRx2Permission
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SplashActivity : BaseActivity(), SplashPresenter.View {

    @Inject lateinit internal var presenter: SplashPresenter

    override fun inject() {
        (applicationContext as App)
                .appComponent
                .splashComponent(SplashModule(this))
                .inject(this)
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_splash
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showPermission()
        Log.i(Const.TAG, "keyHash = " + getKeyHash(this))
    }

    fun showPermission() {
        TedRx2Permission.with(this)
                .setRationaleMessage(R.string.auth_rationale_msg)
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .request()
                .subscribe({ tedPermissionResult ->
                    if (tedPermissionResult.isGranted()) {
                        landingInitSetting()
                    } else {
                        Toast.makeText(this, R.string.auth_denied_msg, Toast.LENGTH_SHORT).show()
                        showPermission()
                    }
                })
    }

    fun landingInitSetting() {
        // 일정 시간 후에 화면 전환
        Flowable.timer(2, TimeUnit.SECONDS)
                .subscribe({
                    var intent: Intent
                    if (presenter.getSettingData(PreferenceName.OIL_TYPE).isEmpty())
                        intent = Intent(this, InitSettingActivity::class.java)
                    else
                        intent = Intent(this, GasStationListActivity::class.java)
                    startActivity(intent)
                    finish()
                })
    }

}