package com.gasgasstation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import com.gasgasstation.App
import com.gasgasstation.R
import com.gasgasstation.base.view.BaseActivity
import com.gasgasstation.constant.Const
import com.gasgasstation.dagger.GasStationListModule
import com.gasgasstation.model.Coords
import com.gasgasstation.presenter.GasStationListPresenter
import kotlinx.android.synthetic.main.activity_gasstation_list.*
import javax.inject.Inject


class GasStationListActivity : BaseActivity(), GasStationListPresenter.View {

    @Inject lateinit internal var presenter: GasStationListPresenter

    lateinit var locationManager: LocationManager

    override fun inject() {
        (applicationContext as App)
                .appComponent
                .gasStationListComponent(GasStationListModule(this))
                .inject(this)
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_gasstation_list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startLocationManager()
    }

    @SuppressLint("MissingPermission")
    fun startLocationManager() {
        locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                Log.i(Const.TAG, "GasStationListActivity latitude = " + location.latitude + " longitude = " + location.longitude)
                presenter.getCoord2address(location.longitude, location.latitude, Coords.WGS84.name)
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, locationListener)
    }

    override fun setCurrentAddress(address: String?) {
        tv_address.text = address
    }

}