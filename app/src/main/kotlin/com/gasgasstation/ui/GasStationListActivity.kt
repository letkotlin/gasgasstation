package com.gasgasstation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.gasgasstation.App
import com.gasgasstation.R
import com.gasgasstation.base.view.BaseActivity
import com.gasgasstation.constant.Const
import com.gasgasstation.dagger.GasStationListModule
import com.gasgasstation.model.Coords
import com.gasgasstation.model.OilType
import com.gasgasstation.model.opinet.GasStation
import com.gasgasstation.presenter.GasStationListPresenter
import com.gasgasstation.ui.adapter.GasStationAdapter
import com.gasgasstation.ui.adapter.GasStationAdapterView
import kotlinx.android.synthetic.main.activity_gasstation_list.*
import javax.inject.Inject


class GasStationListActivity : BaseActivity(), GasStationListPresenter.View {

    @Inject lateinit internal var presenter: GasStationListPresenter
    @Inject lateinit var adapterView: GasStationAdapterView

    val adapter by lazy {
        GasStationAdapter(ArrayList<GasStation>(),
                OilType.B027.name,
                { key, value -> presenter.toString() })
    }

    lateinit var locationManager: LocationManager

    override fun inject() {
        (applicationContext as App)
                .appComponent
                .gasStationListComponent(GasStationListModule(this, adapter))
                .inject(this)
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_gasstation_list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rvGasStation.layoutManager = LinearLayoutManager(this)
        rvGasStation.adapter = adapter
        rvGasStation.isNestedScrollingEnabled = false

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
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0f, locationListener)
    }

    override fun setCurrentAddress(address: String?) {
        tv_address.text = address
    }

    override fun refresh() {
        adapterView.refresh()
    }

}