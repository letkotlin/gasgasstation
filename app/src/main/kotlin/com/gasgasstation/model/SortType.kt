package com.gasgasstation.model

import com.gasgasstation.R

/**
 * Created by kws on 2017. 11. 22..
 */
enum class SortType(val sortType: String) {
    DISTANCE("2"), PRICE("1");

    companion object {
        fun getSortTypeToString(sortType: String): Int =
                when (sortType) {
                    DISTANCE.sortType -> R.string.sort_distance
                    else -> R.string.sort_price
                }
    }
}