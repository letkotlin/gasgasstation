package com.gasgasstation.util

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * Created by indian31 on 2017. 11. 22..
 */

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun String.numberFormat(): String {
    val df = DecimalFormat("#,###")
    df.roundingMode = RoundingMode.CEILING
    return df.format(this.toInt())
}