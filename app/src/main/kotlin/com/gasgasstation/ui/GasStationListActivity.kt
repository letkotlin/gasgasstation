package com.gasgasstation.ui

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.gasgasstation.App
import com.gasgasstation.R
import com.gasgasstation.base.view.BaseActivity
import com.gasgasstation.constant.Const
import com.gasgasstation.constant.Const.Companion.BUS_GET_GAS_LIST
import com.gasgasstation.constant.Const.Companion.BUS_SORT_GAS_LIST
import com.gasgasstation.constant.PreferenceName
import com.gasgasstation.dagger.GasStationListModule
import com.gasgasstation.model.*
import com.gasgasstation.presenter.GasStationListPresenter
import com.gasgasstation.ui.adapter.GasStationAdapter
import com.gasgasstation.ui.adapter.GasStationAdapterView
import com.gasgasstation.util.RxBus
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.firebase.database.*
import com.kakao.kakaonavi.KakaoNaviParams
import com.kakao.kakaonavi.KakaoNaviService
import com.kakao.kakaonavi.NaviOptions
import com.kakao.kakaonavi.options.CoordType
import io.reactivex.Flowable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_gasstation_list.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.ArrayList


class GasStationListActivity : BaseActivity(), GasStationListPresenter.View {

    @Inject
    internal lateinit var presenter: GasStationListPresenter
    @Inject
    lateinit var adapterView: GasStationAdapterView
    lateinit var mDatabase: DatabaseReference
    private lateinit var locationDialog: AlertDialog
    private lateinit var noticeDialog: AlertDialog

    private val REQ_CODE_LOCATION = 1515

    val adapter by lazy {
        GasStationAdapter(ArrayList(), OilType.B027.name) { x, y, name -> landingMap(x, y, name) }
    }

    private fun landingMap(x: String, y: String, name: String) {
        val mapType = presenter.getSettingData(PreferenceName.MAP_TYPE)
        if (mapType == MapType.TMAP.map)
            presenter.landingTmap(x.toDouble(), y.toDouble(), name, Coords.KTM.name, Coords.WGS84.name)
        if (mapType == MapType.KAKAO.map)
            presenter.landingKaKaoMap(x.toDouble(), y.toDouble(), name, Coords.KTM.name, Coords.WGS84.name)
    }

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
        mDatabase = FirebaseDatabase.getInstance().reference;
        getOpinetKey()
        reqGasList()
        showNotice()
        Flowable.interval(1, TimeUnit.SECONDS)
                .takeWhile { GeoCode.latitude == null || GeoCode.longitude == null }
                .doOnComplete {
                    reqGasList()
                    presenter.getCoord2address(GeoCode.longitude!!, GeoCode.latitude!!, Coords.WGS84.name)
                }.subscribe()

        MobileAds.initialize(this, getString(R.string.ADMOB_AD_ID))
        adView.adListener = object : AdListener() {
            override fun onAdFailedToLoad(p0: Int) {
                super.onAdFailedToLoad(p0)
                Log.i(Const.TAG, "onAdFailedToLoad() errorCode = " + p0)
                adView.visibility = View.GONE
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                adView.visibility = View.VISIBLE
            }
        }
        adView.loadAd(AdRequest.Builder().build())

        rv_gas_station.layoutManager = LinearLayoutManager(this)
        rv_gas_station.adapter = adapter

        swipe_layer.setOnRefreshListener {
            reqGasList()
        }
        swipe_layer.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent))

        val sortText = presenter.getSettingData(PreferenceName.SORT_TYPE)
        Log.i(Const.TAG, "sortText = " + sortText)
        tv_sort.text = if (sortText == getString(R.string.sort_distance)) getString(R.string.sort_price) else getString(R.string.sort_distance)
        tv_sort.setOnClickListener {
            if (tv_sort.text == getString(R.string.sort_distance)) {
                presenter.saveSettingData(PreferenceName.SORT_TYPE, SortType.DISTANCE.sortType)
                presenter.sortList(SortType.DISTANCE)
                tv_sort.text = getString(R.string.sort_price)
            } else {
                presenter.saveSettingData(PreferenceName.SORT_TYPE, SortType.PRICE.sortType)
                presenter.sortList(SortType.PRICE)
                tv_sort.text = getString(R.string.sort_distance)
            }
        }

        tv_setting.setOnClickListener {
            var intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        RxBus.subscribe(BUS_GET_GAS_LIST, this, Consumer {
            Log.i(Const.TAG, "BUS_GET_GAS_LIST ")
            reqGasList()
        })

        RxBus.subscribe(BUS_SORT_GAS_LIST, this, Consumer {
            Log.i(Const.TAG, "BUS_SORT_GAS_LIST sortType")
            tv_sort.performClick()
        })
    }

    private fun showNotice() {
        var eventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                Log.i(Const.TAG, "showNotice() DataSnapshot = " + p0.value)
                if (p0.exists()) {
                    showNoticeDialog(p0.value.toString())
                }
            }
        }
        mDatabase.child("notice").addListenerForSingleValueEvent(eventListener)
    }

    private fun showNoticeDialog(msg: String) {
        noticeDialog = AlertDialog.Builder(this, R.style.AlertDialogTheme)
                .setTitle(R.string.location_setting_title)
                .setMessage(msg)
                .setCancelable(true)
                .setPositiveButton(R.string.close) { _, _ ->
                    dismissLocationDialog()
                }.create()
        noticeDialog.show()
    }

    private fun reqGasList() {

        if (GeoCode.latitude == null || GeoCode.longitude == null) {
            if (swipe_layer.isRefreshing) swipe_layer.isRefreshing = false
            checkGPS()
            return
        }

        if (OpinetKey.keys.isEmpty())
            getOpinetKey()

        /*
         오늘 날짜의 총 조회 건수를 조회합니다.
         1. 오늘 날짜의 조회 건수가 없다면 기존 데이터를 모두 삭제 후 오늘 날짜 값으로 1셋팅합니다.
         2. 오늘 날짜의 조회 건수가 있다면 현재 개수 + 1 해줍니다.
         3. 개수 / 1000으로 하여 조회할 때 사용할 key를 셋팅합니다.
         */
        val now = getTodayDate()
        var eventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                Log.i(Const.TAG, "setRequestCnt() now = " + now + " p0 = " + p0.value)
                var requestCnt = p0.value
                requestCnt = if (requestCnt == null)
                    1
                else
                    requestCnt.toString().toInt().plus(1)
                mDatabase.child("request_cnt").child(now).setValue(requestCnt)

                if (requestCnt / 1000 < 3) {
                    Const.OPINET_API_KEY = OpinetKey.keys[requestCnt / 1000].trim()
                } else
                    Const.OPINET_API_KEY = OpinetKey.keys[2].trim()
                Log.i(Const.TAG, "Const.OPINET_API_KEY = " + Const.OPINET_API_KEY)
                presenter.getGasStationList(GeoCode.longitude!!, GeoCode.latitude!!, Coords.WGS84.name, Coords.KTM.name)
            }
        }
        mDatabase.child("request_cnt").child(getTodayDate()).addListenerForSingleValueEvent(eventListener)
    }

    private fun getOpinetKey() {
        var eventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                //@TODO DataSnapshot to array 방법이 없나?
                Log.i(Const.TAG, "getOpinetKey() p0 = " + p0.value)
                OpinetKey.keys = p0.value.toString().replace("[", "").replace("]", "").split(",")
            }
        }
        mDatabase.child("keys").addListenerForSingleValueEvent(eventListener)
    }

    private fun getTodayDate(): String {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyyMMdd")
        return sdf.format(calendar.time)
    }

    override fun setCurrentAddress(address: String?) {
        tv_address.text = address
    }

    override fun refresh() {
        adapterView.refresh()
        if (swipe_layer.isRefreshing) swipe_layer.isRefreshing = false
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.unregister(this)
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


    private fun checkGPS() {
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gpsProvider = LocationManager.GPS_PROVIDER
        val networkProvider = LocationManager.NETWORK_PROVIDER

        if (locationManager.isProviderEnabled(gpsProvider) || locationManager.isProviderEnabled(networkProvider)) {
            runOnUiThread { Toast.makeText(this, R.string.loading_location, Toast.LENGTH_SHORT).show() }
        } else {
            runOnUiThread {
                locationDialog = AlertDialog.Builder(this, R.style.AlertDialogTheme)
                        .setTitle(R.string.location_setting_title)
                        .setMessage(R.string.location_setting_message)
                        .setCancelable(false)
                        .setPositiveButton(R.string.location_setting) { _, _ ->
                            dismissLocationDialog()
                            startActivityForResult(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), REQ_CODE_LOCATION)
                        }
                        .setNegativeButton(R.string.close) { _, _ ->
                            dismissLocationDialog()
                        }.create()
            }
            showLocationDialog()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE_LOCATION) {
            checkGPS()
        }
    }

    private fun dismissLocationDialog() {
        try {
            locationDialog.dismiss()
        } catch (e: Exception) {
        }
    }

    private fun showLocationDialog() {
        try {
            if (!locationDialog.isShowing)
                locationDialog.show()
        } catch (e: Exception) {
        }
    }

}

