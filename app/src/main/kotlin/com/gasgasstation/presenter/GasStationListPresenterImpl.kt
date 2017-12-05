package com.gasgasstation.presenter

import com.gasgasstation.BuildConfig
import com.gasgasstation.api.DaumApi
import com.gasgasstation.api.OpinetApi
import com.gasgasstation.constant.PreferenceName
import com.gasgasstation.dagger.PreferenceUtil
import com.gasgasstation.model.Coords
import com.gasgasstation.model.OilType
import com.gasgasstation.model.daum.Coord2address
import com.gasgasstation.model.daum.TransCoord
import com.gasgasstation.model.opinet.OPINET
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by kws on 2017. 11. 28..
 */
class GasStationListPresenterImpl @Inject internal constructor(private val view: GasStationListPresenter.View, private val preference: PreferenceUtil, private val daumApi: DaumApi, private val opinetApi: OpinetApi) : GasStationListPresenter {

    fun findAllGasStation(code: String, x: Double, y: Double, radius: String, sort: String, prodcd: String, out: String) {
        var flowable: Flowable<OPINET> = opinetApi.findAllGasStation(code, x, y, radius, sort, prodcd, out)
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, { it.printStackTrace() })
    }

    fun transCoord(x: Double, y: Double, inputCoord: String, outputCoord: String) {
        var flowable: Flowable<TransCoord> = daumApi.tanscoord(x, y, inputCoord, outputCoord)
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    var coordDocument = it.documents?.get(0)!!
                    findAllGasStation(BuildConfig.OPINET_API_KEY, coordDocument.x, coordDocument.y,
                            getSettingData(PreferenceName.DISTANCE_TYPE)!!, getSettingData(PreferenceName.SORT_TYPE)!!,
                            getSettingData(PreferenceName.OIL_TYPE)!!, "json")
                }, { it.printStackTrace() })

    }

    override fun getCoord2address(x: Double, y: Double, inputCoord: String) {
        var flowable: Flowable<Coord2address> = daumApi.coord2address(x, y, inputCoord)
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.setCurrentAddress(it.documents?.get(0)?.address?.address_name)
                    transCoord(x, y, Coords.WGS84.name, Coords.KTM.name)
                }, { it.printStackTrace() })
    }

    override fun getSettingData(key: String): String? = preference.getString(key, "")
}