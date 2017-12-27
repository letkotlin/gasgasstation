package com.gasgasstation.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.gasgasstation.App
import com.gasgasstation.R
import com.gasgasstation.base.view.BaseActivity
import com.gasgasstation.constant.Const
import com.gasgasstation.constant.PreferenceName
import com.gasgasstation.dagger.SplashModule
import com.gasgasstation.model.GeoCode
import com.gasgasstation.presenter.SplashPresenter
import com.kakao.util.helper.Utility.getKeyHash
import com.tedpark.tedpermission.rx2.TedRx2Permission
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SplashActivity : BaseActivity(), SplashPresenter.View {

    @Inject lateinit internal var presenter: SplashPresenter
    lateinit var locationManager: LocationManager
    lateinit var locationListener: LocationListener

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
                        initLocationManager()
                        reqLocationUpdate()
                        landingInitSetting()
                    } else {
                        Toast.makeText(this, R.string.auth_denied_msg, Toast.LENGTH_SHORT).show()
                        showPermission()
                    }
                })
    }

    fun landingInitSetting() {
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

    @SuppressLint("MissingPermission")
    fun initLocationManager() {
        locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                Log.i(Const.TAG, "SplashActivity latitude = " + location.latitude + " longitude = " + location.longitude)
                GeoCode.latitude = location.latitude
                GeoCode.longitude = location.longitude
                locationManager.removeUpdates(locationListener)
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }
    }

    @SuppressLint("MissingPermission")
    fun reqLocationUpdate() {
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 0f, locationListener)
    }

}