package com.gasgasstation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.gasgasstation.App
import com.gasgasstation.R
import com.gasgasstation.base.view.BaseActivity
import com.gasgasstation.constant.Const
import com.gasgasstation.constant.PreferenceName
import com.gasgasstation.dagger.GasStationListModule
import com.gasgasstation.model.Coords
import com.gasgasstation.model.OilType
import com.gasgasstation.model.SortType
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
    lateinit var locationListener: LocationListener

    var longitude: Double? = null
    var latitude: Double? = null

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

        rv_gas_station.layoutManager = LinearLayoutManager(this)
        rv_gas_station.adapter = adapter

        swipe_layer.setOnRefreshListener { reqLocationUpdate() }
        swipe_layer.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent))

        tv_sort.text = presenter.getSettingData(PreferenceName.SORT_TYPE)
        tv_sort.setOnClickListener({
            if (tv_sort.text == getString(R.string.sort_distance)) {
                presenter.sortList(SortType.PRICE)
                tv_sort.text = getString(R.string.sort_price)
            } else {
                presenter.sortList(SortType.DISTANCE)
                tv_sort.text = getString(R.string.sort_distance)
            }
        })

        tv_setting.setOnClickListener {
            var intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        initLocationManager()
        reqLocationUpdate()
    }

    @SuppressLint("MissingPermission")
    fun initLocationManager() {
        locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                Log.i(Const.TAG, "GasStationListActivity latitude = " + location.latitude + " longitude = " + location.longitude)
                presenter.transCoord(location.longitude, location.latitude, Coords.WGS84.name, Coords.KTM.name)
                presenter.getCoord2address(location.longitude, location.latitude, Coords.WGS84.name)
                locationManager.removeUpdates(locationListener)
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }
    }

    @SuppressLint("MissingPermission")
    fun reqLocationUpdate() {
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, locationListener)
    }

    override fun setCurrentAddress(address: String?) {
        tv_address.text = address
    }

    override fun refresh() {
        adapterView.refresh()
        if (swipe_layer.isRefreshing)
            swipe_layer.isRefreshing = false
    }

    override fun onDestroy() {
        super.onDestroy()
        locationManager.removeUpdates(locationListener)
    }
}