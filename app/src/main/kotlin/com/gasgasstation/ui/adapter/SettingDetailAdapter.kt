package com.gasgasstation.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gasgasstation.R
import com.gasgasstation.base.adapter.AdapterModel
import com.gasgasstation.base.adapter.AdapterView
import com.gasgasstation.model.Setting
import kotlinx.android.synthetic.main.item_init_setting.view.*

/**
 * Created by kws on 2017. 11. 30..
 */
interface SettingDetailAdapterModel : AdapterModel<Setting>

interface SettingDetailAdapterView : AdapterView

class SettingDetailAdapter(private val items: ArrayList<Setting> = ArrayList<Setting>(),
                           private val onClick: (String) -> Unit) : RecyclerView.Adapter<SettingDetailAdapter.SettingDetailViewHolder>(), SettingDetailAdapterModel, SettingDetailAdapterView {

    override fun onBindViewHolder(holder: SettingDetailViewHolder?, position: Int) {
        val item = getItem(position)
        holder?.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SettingDetailViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_setting_detail, parent, false)
        return SettingDetailViewHolder(view, onClick)
    }

    override fun refresh() {
        notifyDataSetChanged()
    }

    override fun addItems(items: List<Setting>) {
        this.items.addAll(items)
    }

    override fun addItem(item: Setting) {
        this.items.add(item)
    }

    override fun getItem(position: Int): Setting? {
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

    class SettingDetailViewHolder(itemView: View, val onClick: (String) -> Unit) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Setting?) {
            if (item == null)
                return

            itemView.tv_title.text = item.name
            itemView.iv_check.visibility = if (item.isChecked == true) View.VISIBLE else View.GONE
            itemView.ll_root.setOnClickListener {
                onClick.invoke(item.name)
            }
        }
    }

}
