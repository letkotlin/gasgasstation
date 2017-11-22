package com.gasgasstation.view

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.gasgasstation.R
import kotlinx.android.synthetic.main.initial_setting_recycler_view_row.view.*

/**
 * Created by indian31 on 2017. 11. 22..
 */


class RecyclerAdapter(private val datas:List<String>, private val listener: ((String, String) -> Unit)) : RecyclerView.Adapter<RecyclerAdapter.StringHolder>() {

    override fun onBindViewHolder(holder: RecyclerAdapter.StringHolder, position: Int) {
        holder.bindValue(datas[position])

        holder.rootView.setOnClickListener {
            listener("11","22")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.StringHolder {
        val inflatedView = parent.inflate(R.layout.initial_setting_recycler_view_row, false)
        return StringHolder(inflatedView)
    }

    override fun getItemCount() = datas.size

    class StringHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        var rootView:View = v
        private var name:String? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Log.d("RecyclerAdapter","item click")
        }

        fun bindValue(value:String) {
            this.name = value
            rootView.itemName.text = value
        }

    }
}
