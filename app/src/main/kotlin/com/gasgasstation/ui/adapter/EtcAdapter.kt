package com.gasgasstation.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gasgasstation.R
import com.gasgasstation.base.adapter.AdapterModel
import com.gasgasstation.base.adapter.AdapterView
import kotlinx.android.synthetic.main.item_init_setting.view.*

/**
 * Created by kws on 2017. 11. 30..
 */
class EtcAdapter(private val items: ArrayList<String> = ArrayList<String>(), private val onClick: (String) -> Unit) : RecyclerView.Adapter<EtcAdapter.EtcSettingViewHolder>(), AdapterModel<String>, AdapterView {

    override fun onBindViewHolder(holder: EtcSettingViewHolder?, position: Int) {
        val item = getItem(position)
        holder?.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): EtcSettingViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_setting, parent, false)
        return EtcSettingViewHolder(view, onClick)
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

    class EtcSettingViewHolder(itemView: View, val onClick: (String) -> Unit) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: String?) {
            if (item == null)
                return

            itemView.tv_title.text = item
            itemView.ll_root.setOnClickListener { onClick.invoke(item) }
        }
    }

}
