package com.gasgasstation.presenter

import com.gasgasstation.api.Api
import com.gasgasstation.dagger.PreferenceUtil
import com.gasgasstation.model.Coords
import com.gasgasstation.model.daum.Coord2address
import com.gasgasstation.model.daum.TransCoord
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by kws on 2017. 11. 28..
 */
class GasStationListPresenterImpl @Inject internal constructor(private val view: GasStationListPresenter.View, private val preference: PreferenceUtil, private val api: Api) : GasStationListPresenter {
    fun transCoord(x: Double, y: Double, inputCoord: String, outputCoord: String) {
        var flowable: Flowable<TransCoord> = api.tanscoord(x, y, inputCoord, outputCoord)
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, { it.printStackTrace() })
    }

    override fun getCoord2address(x: Double, y: Double, inputCoord: String) {
        var flowable: Flowable<Coord2address> = api.coord2address(x, y, inputCoord)
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.setCurrentAddress(it.documents?.get(0)?.address?.address_name)
                    transCoord(x, y, Coords.WGS84.name, Coords.TM.name)
                }, { it.printStackTrace() })
    }

    override fun getSettingData(key: String): String? = preference.getString(key, "")
}