package com.gasgasstation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.gasgasstation.App
import com.gasgasstation.R
import com.gasgasstation.base.view.BaseActivity
import com.gasgasstation.constant.Const
import com.gasgasstation.constant.PreferenceName
import com.gasgasstation.dagger.GasStationListModule
import com.gasgasstation.model.Coords
import com.gasgasstation.model.MapType
import com.gasgasstation.model.OilType
import com.gasgasstation.model.SortType
import com.gasgasstation.model.opinet.GasStation
import com.gasgasstation.presenter.GasStationListPresenter
import com.gasgasstation.ui.adapter.GasStationAdapter
import com.gasgasstation.ui.adapter.GasStationAdapterView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.firebase.database.*
import com.kakao.kakaonavi.KakaoNaviParams
import com.kakao.kakaonavi.KakaoNaviService
import com.kakao.kakaonavi.NaviOptions
import com.kakao.kakaonavi.options.CoordType
import kotlinx.android.synthetic.main.activity_gasstation_list.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class GasStationListActivity : BaseActivity(), GasStationListPresenter.View {

    @Inject lateinit internal var presenter: GasStationListPresenter
    @Inject lateinit var adapterView: GasStationAdapterView
    lateinit var mDatabase: DatabaseReference
    lateinit var opinetKey: List<String>

    val adapter by lazy {
        GasStationAdapter(ArrayList<GasStation>(),
                OilType.B027.name,
                { x, y, name -> landingMap(x, y, name) })
    }

    private fun landingMap(x: String, y: String, name: String) {
        val mapType = presenter.getSettingData(PreferenceName.MAP_TYPE)
        if (mapType == MapType.TMAP.map)
            presenter.landingTmap(x.toDouble(), y.toDouble(), name, Coords.KTM.name, Coords.WGS84.name)
        if (mapType == MapType.KAKAO.map)
            presenter.landingKaKaoMap(x.toDouble(), y.toDouble(), name, Coords.KTM.name, Coords.WGS84.name)
    }

    lateinit var locationManager: LocationManager
    lateinit var locationListener: LocationListener

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
        mDatabase = FirebaseDatabase.getInstance().getReference();

        MobileAds.initialize(this, Const.ADMOB_APP_ID);
        adView.loadAd(AdRequest.Builder().build())

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
        getOpinetKey()
    }

    /*
     오늘 날짜의 총 조회 건수를 조회합니다.
     1. 오늘 날짜의 조회 건수가 없다면 기존 데이터를 모두 삭제 후 오늘 날짜 값으로 1셋팅합니다.
     2. 오늘 날짜의 조회 건수가 있다면 현재 개수 + 1 해줍니다.
     3. 개수 / 1000으로 하여 조회할 때 사용할 key를 셋팅합니다.
     */
    private fun setRequestCnt() {
        val now = getTodayDate()
        var eventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(p0: DataSnapshot?) {
                Log.i(Const.TAG, "setRequestCnt() p0 = " + p0?.value)
                var requestCnt = p0?.value.toString().toInt()
                if (requestCnt == null) {
                    mDatabase.child("request_cnt").child(now).setValue(1)
                    requestCnt = 1
                } else {
                    requestCnt = requestCnt++
                    mDatabase.child("request_cnt").child(now).setValue(requestCnt)
                }
                if (requestCnt / 1000 < 3) {
                    Const.OPINET_API_KEY = opinetKey[requestCnt / 1000]
                } else
                    Const.OPINET_API_KEY = opinetKey[3]
                Log.i(Const.TAG, "Const.OPINET_API_KEY = " + Const.OPINET_API_KEY)
            }
        }
        mDatabase.child("request_cnt").child(getTodayDate()).addListenerForSingleValueEvent(eventListener)
    }

    private fun getOpinetKey() {
        var eventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(p0: DataSnapshot?) {
                //@TODO DataSnapshot to array 방법이 없나?
                Log.i(Const.TAG, "getOpinetKey() p0 = " + p0?.value)
                opinetKey = p0?.value.toString().replace("[", "").replace("]", "").split(",")
            }
        }
        mDatabase.child("keys").addListenerForSingleValueEvent(eventListener)
    }

    private fun getTodayDate(): String {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyyMMdd")
        return sdf.format(calendar.time)
    }

    @SuppressLint("MissingPermission")
    fun initLocationManager() {
        locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                Log.i(Const.TAG, "GasStationListActivity latitude = " + location.latitude + " longitude = " + location.longitude)
                setRequestCnt()
                presenter.getGasStationList(location.longitude, location.latitude, Coords.WGS84.name, Coords.KTM.name)
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

    // TODO 한국 구글지도는 길찾기를 지원하지 않음
    override fun openGoogleMap(x: Double, y: Double) {
        val gmmIntentUri = Uri.parse("google.navigation:q=" + y + "," + x + "&mode=d")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.`package` = "com.google.android.apps.maps"
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent)
        } else
            Toast.makeText(this, R.string.not_install_google_map, Toast.LENGTH_SHORT).show()
    }

    override fun openTmap(x: Double, y: Double, name: String) {
        var landingUrl = "http://m.tmap.co.kr/tmap2/mobile/route.jsp?name=" + name + "&lon=" + x + "&lat=" + y
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(landingUrl))
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(browserIntent)
    }

    override fun openKakaomap(x: Double, y: Double, name: String) {
        val destination = com.kakao.kakaonavi.Location.newBuilder(name, x, y).build()
        val builder = KakaoNaviParams.newBuilder(destination)
                .setNaviOptions(NaviOptions.newBuilder().setCoordType(CoordType.WGS84).build())
        KakaoNaviService.shareDestination(this, builder.build())
    }

}