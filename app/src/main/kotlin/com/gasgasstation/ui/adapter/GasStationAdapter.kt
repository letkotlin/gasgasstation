package com.gasgasstation.ui.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gasgasstation.R
import com.gasgasstation.base.adapter.AdapterModel
import com.gasgasstation.base.adapter.AdapterView
import com.gasgasstation.constant.Const
import com.gasgasstation.model.GasStationType.Companion.getGasStationImg
import com.gasgasstation.model.SortType
import com.gasgasstation.model.opinet.GasStation
import com.gasgasstation.util.numberFormat
import kotlinx.android.synthetic.main.item_gas_station.view.*

/**
 * Created by kws on 2017. 11. 30..
 */
interface GasStationAdapterModel : AdapterModel<GasStation> {
    fun setOilType(oilType: String)
    fun sortList(sortType: SortType)
}

interface GasStationAdapterView : AdapterView

class GasStationAdapter(private val items: ArrayList<GasStation> = ArrayList(),
                        private var oilType: String,
                        private val onClick: (String, String, String) -> Unit) : RecyclerView.Adapter<GasStationAdapter.GasStationHolder>(), GasStationAdapterModel, GasStationAdapterView {

    override fun onBindViewHolder(holder: GasStationHolder?, position: Int) {
        val item = getItem(position)
        holder?.bind(item, oilType)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GasStationHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_gas_station, parent, false)
        return GasStationHolder(view, onClick)
    }

    override fun refresh() {
        notifyDataSetChanged()
    }

    override fun addItems(items: List<GasStation>) {
        this.items.addAll(items)
    }

    override fun addItem(item: GasStation) {
        this.items.add(item)
    }

    override fun getItem(position: Int): GasStation? {
        if (position in 0..size()) {
            return items[position]
        } else {
            return null
        }
    }

    override fun clear() {
        items.clear()
    }

    override fun size(): Int {
        return itemCount
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun setOilType(oilType: String) {
        this.oilType = oilType
    }

    override fun sortList(sortType: SortType) {
        if (SortType.DISTANCE == sortType)
            this.items.sortWith(compareBy({ it.DISTANCE }))
        else
            this.items.sortWith(compareBy({ it.PRICE }))
    }

    class GasStationHolder(itemView: View, val onClick: (String, String, String) -> Unit) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: GasStation?, oilType: String) {
            if (item == null)
                return

            Log.i(Const.TAG, "GasStationHolder item = " + item.toString())
            itemView.iv_station_type.setImageResource(getGasStationImg(item.POLL_DIV_CD))
            itemView.tv_oil_type.text = oilType
            itemView.tv_os_nm.text = item.OS_NM
            itemView.tv_price.text = item.PRICE.toString().numberFormat()
            itemView.tv_distance.text = "%.1f".format(item.DISTANCE / 1000)
            itemView.ll_root.setOnClickListener { onClick.invoke(item.GIS_X_COOR, item.GIS_Y_COOR, item.OS_NM) }
        }
    }

}
