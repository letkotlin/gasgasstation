package com.gasgasstation.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
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
import com.google.firebase.database.*
import com.kakao.util.helper.Utility.getKeyHash
import com.tedpark.tedpermission.rx2.TedRx2Permission
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SplashActivity : BaseActivity(), SplashPresenter.View {

    @Inject
    internal lateinit var presenter: SplashPresenter
    lateinit var locationManager: LocationManager
    lateinit var locationListener: LocationListener
    lateinit var mDatabase: DatabaseReference

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
        mDatabase = FirebaseDatabase.getInstance().reference;
        checkVer()
        Log.i(Const.TAG, "keyHash = " + getKeyHash(this))
    }

    private fun showPermission() {
        TedRx2Permission.with(this)
                .setRationaleMessage(R.string.auth_rationale_msg)
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .request()
                .subscribe { tedPermissionResult ->
                    if (tedPermissionResult.isGranted) {
                        reqLocationUpdate()
                        landingInitSetting()
                    } else {
                        Toast.makeText(this, R.string.auth_denied_msg, Toast.LENGTH_SHORT).show()
                        showPermission()
                    }
                }
    }

    private fun checkVer() {
        var eventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                Log.i(Const.TAG, "checkVer() p0 = " + p0.value)
                var playVersion = p0.value.toString().replace(".", "").toInt()
                if (playVersion > getCurrentVersion()) {
                    landingUpdateMarket()
                } else {
                    showPermission()
                }
            }
        }
        mDatabase.child("versions").child("android").addListenerForSingleValueEvent(eventListener)
    }

    private fun landingInitSetting() {
        Flowable.timer(2, TimeUnit.SECONDS)
                .subscribe {
                    var intent: Intent
                    if (presenter.getSettingData(PreferenceName.OIL_TYPE).isEmpty())
                        intent = Intent(this, InitSettingActivity::class.java)
                    else
                        intent = Intent(this, GasStationListActivity::class.java)
                    startActivity(intent)
                    finish()
                }
    }

    @SuppressLint("MissingPermission")
    private fun reqLocationUpdate() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
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
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, locationListener)
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
    }

    private fun getCurrentVersion(): Int {
        var ver = 0
        return try {
            ver = applicationContext.packageManager.getPackageInfo(packageName, PackageManager.GET_META_DATA).versionName.replace(".", "").toInt()
            Log.i(Const.TAG, "getCurrentVersion ver = " + ver)
            ver
        } catch (e: PackageManager.NameNotFoundException) {
            ver
        }

    }

    private fun landingUpdateMarket() {
        try {
            AlertDialog.Builder(this, R.style.AlertDialogTheme)
                    .setMessage(R.string.alert_new_version)
                    .setCancelable(false)
                    .setPositiveButton(R.string.update) { dialog, _ ->
                        dialog.dismiss()
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)))
                        finish()
                    }
                    .setNegativeButton(R.string.close) { dialog, _ ->
                        dialog.dismiss()
                        finish()
                    }.show()

        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, R.string.alert_empty_market_message, Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, R.string.alert_fail_connect_market, Toast.LENGTH_SHORT).show()
        }

    }
}