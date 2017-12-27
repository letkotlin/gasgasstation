package com.gasgasstation.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.gasgasstation.R
import com.gasgasstation.base.view.BaseActivity
import com.gasgasstation.constant.Const
import com.gasgasstation.ui.adapter.EtcAdapter
import com.gasgasstation.ui.adapter.FindSettingAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_setting.*


class SettingActivity : BaseActivity() {

    override fun inject() {

    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_setting
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MobileAds.initialize(this, Const.ADMOB_APP_ID)
        adView.loadAd(AdRequest.Builder().build())

        val findSettings: ArrayList<String> = arrayListOf(getString(R.string.find_distance), getString(R.string.oil_type),
                getString(R.string.gas_station_type), getString(R.string.sort_type), getString(R.string.map_type))
        val findSettingAdapter = FindSettingAdapter(findSettings, { key -> landingSettingDeatil(key) })
        rv_find_setting.layoutManager = LinearLayoutManager(this)
        rv_find_setting.adapter = findSettingAdapter
        rv_find_setting.addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager(this).orientation))
        findSettingAdapter.refresh()

        val etcs: ArrayList<String> = arrayListOf(getString(R.string.maker), getString(R.string.reporting), getString(R.string.review_write))
        val etcAdapter = EtcAdapter(etcs, { key -> landingEtc(key) })
        rv_etc.layoutManager = LinearLayoutManager(this)
        rv_etc.adapter = etcAdapter
        rv_etc.addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager(this).orientation))
        etcAdapter.refresh()

        tv_close.setOnClickListener { finish() }
    }

    private fun landingSettingDeatil(name: String) {
        startActivity(Intent(this, SettingDetailActivity::class.java).putExtra(Const.SETTING_TYPE, name))
    }

    private fun landingEtc(name: String) {
        when (name) {
            getString(R.string.maker) -> startActivity(Intent(this, MakerActivity::class.java))
            getString(R.string.reporting) -> landingEmail()
            getString(R.string.review_write) -> landingMarket()
        }
    }

    private fun landingEmail() {
        val mailto = "mailto:coreanim@gmail.com" +
                "?cc=" + "indian311@icloud.com, ihryu@me.com, hongilyoon@gmail.com" +
                "&subject=" + Uri.encode(getString(R.string.email_subject))
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse(mailto)
        try {
            startActivity(emailIntent)
        } catch (e: Exception) {
            Toast.makeText(this, R.string.email_fail, Toast.LENGTH_SHORT).show()
        }
    }

    private fun landingMarket() {
        val appPackageName = packageName
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)))
        } catch (anfe: android.content.ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)))
        }

    }
}