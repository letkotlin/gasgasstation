package com.gasgasstation.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gasgasstation.R
import com.gasgasstation.base.adapter.AdapterModel
import com.gasgasstation.base.adapter.AdapterView
import com.gasgasstation.constant.PreferenceName
import kotlinx.android.synthetic.main.item_init_setting.view.*

/**
 * Created by kws on 2017. 11. 30..
 */
interface OilAdapterModel : AdapterModel<String>

interface OilAdapterView : AdapterView

class OilAdapter(private val items: ArrayList<String> = ArrayList<String>(), private val onClick: (String, String) -> Unit) : RecyclerView.Adapter<OilAdapter.InitialSettingViewHolder>(), OilAdapterView, OilAdapterModel {

    override fun onBindViewHolder(holder: InitialSettingViewHolder?, position: Int) {
        val item = getItem(position)
        holder?.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): InitialSettingViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_init_setting, parent, false)
        return InitialSettingViewHolder(view, onClick)
    }

    override fun refresh() {
        notifyDataSetChanged()
    }

    override fun addItems(items: List<String>) {
        this.items.addAll(items)
    }

    override fun addItem(item: String) {
        this.items.add(item)
    }

    override fun getItem(position: Int): String? {
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

    class InitialSettingViewHolder(itemView: View, val onClick: (String, String) -> Unit) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: String?) {
            if (item == null)
                return
            itemView.tv_title.text = item
            itemView.ll_root.setOnClickListener {
                onClick.invoke(PreferenceName.OIL_TYPE, item)
            }
        }
    }

}
