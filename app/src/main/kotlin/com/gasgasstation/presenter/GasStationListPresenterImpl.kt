package com.gasgasstation.presenter

import android.util.Log
import com.gasgasstation.BuildConfig
import com.gasgasstation.api.DaumApi
import com.gasgasstation.api.OpinetApi
import com.gasgasstation.constant.Const
import com.gasgasstation.constant.PreferenceName
import com.gasgasstation.dagger.PreferenceUtil
import com.gasgasstation.model.DistanceType
import com.gasgasstation.model.GasStationType
import com.gasgasstation.model.OilType
import com.gasgasstation.model.SortType
import com.gasgasstation.model.daum.Coord2address
import com.gasgasstation.model.daum.TransCoord
import com.gasgasstation.model.opinet.OPINET
import com.gasgasstation.ui.adapter.GasStationAdapterModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by kws on 2017. 11. 28..
 */
class GasStationListPresenterImpl @Inject internal constructor(private val view: GasStationListPresenter.View, private val preference: PreferenceUtil, private val daumApi: DaumApi,
                                                               private val opinetApi: OpinetApi, private val adapterModel: GasStationAdapterModel) : GasStationListPresenter {
    override fun landingKaKaoMap(x: Double, y: Double, name: String, inputCoord: String, outputCoord: String) {
        var flowable: Flowable<TransCoord> = daumApi.tanscoord(x, y, inputCoord, outputCoord)
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    var coordDocument = it.documents?.get(0)!!
                    view.openKakaomap(coordDocument.x, coordDocument.y, name)
                }, { it.printStackTrace() })
    }

    override fun landingTmap(x: Double, y: Double, name: String, inputCoord: String, outputCoord: String) {
        var flowable: Flowable<TransCoord> = daumApi.tanscoord(x, y, inputCoord, outputCoord)
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    var coordDocument = it.documents?.get(0)!!
                    view.openTmap(coordDocument.x, coordDocument.y, name)
                }, { it.printStackTrace() })
    }

    override fun landingGoogleMap(x: Double, y: Double, inputCoord: String, outputCoord: String) {
        var flowable: Flowable<TransCoord> = daumApi.tanscoord(x, y, inputCoord, outputCoord)
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    var coordDocument = it.documents?.get(0)!!
                    view.openGoogleMap(coordDocument.x, coordDocument.y)
                }, { it.printStackTrace() })
    }

    override fun sortList(sortType: SortType) {
        adapterModel.sortList(sortType)
        view.refresh()
    }

    fun findAllGasStation(code: String, x: Double, y: Double, radius: String, sort: String, prodcd: String, out: String) {
        val gasStationType = GasStationType.getGasStation(getSettingData(PreferenceName.GAS_STATION_TYPE))
        Log.i(Const.TAG, "findAllGasStation gasStationType = " + gasStationType)
        var flowable: Flowable<OPINET> = opinetApi.findAllGasStation(code, x, y, radius, sort, prodcd, out)
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { response -> Flowable.fromArray(response.RESULT.OIL) }
                .flatMap { items -> Flowable.fromIterable(items) }
                .filter { item -> (GasStationType.ALL.name == gasStationType || item.POLL_DIV_CD == gasStationType) }
                .toList()
                .subscribe({
                    adapterModel.setOilType(OilType.getOilName(prodcd))
                    adapterModel.clear()
                    adapterModel.addItems(it)
                    view.refresh()
                }, { it.printStackTrace() })
    }

    override fun getGasStationList(x: Double, y: Double, inputCoord: String, outputCoord: String) {
        var flowable: Flowable<TransCoord> = daumApi.tanscoord(x, y, inputCoord, outputCoord)
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    var coordDocument = it.documents?.get(0)!!
                    findAllGasStation(Const.OPINET_API_KEY, coordDocument.x, coordDocument.y,
                            DistanceType.getDistance(getSettingData(PreferenceName.DISTANCE_TYPE)),
                            SortType.getSort(getSettingData(PreferenceName.SORT_TYPE)),
                            OilType.getOilType(getSettingData(PreferenceName.OIL_TYPE)), "json")
                }, { it.printStackTrace() })

    }

    override fun getCoord2address(x: Double, y: Double, inputCoord: String) {
        var flowable: Flowable<Coord2address> = daumApi.coord2address(x, y, inputCoord)
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.setCurrentAddress(it.documents?.get(0)?.address?.address_name)
                }, { it.printStackTrace() })
    }

    override fun getSettingData(key: String): String = preference.getString(key, "")

    override fun saveSettingData(key: String, value: String) {
        preference.put(key, value)
    }
}