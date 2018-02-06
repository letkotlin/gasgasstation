package com.gasgasstation.base.adapter

/**
 * Created by kws on 2017. 11. 29..
 */
interface AdapterModel<T> {
    fun addItems(items: List<T>)

    fun addItem(item: T)

    fun getItem(position: Int): T?

    fun clear()

    fun size(): Int

}

interface AdapterView {
    fun refresh()
}