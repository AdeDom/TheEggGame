package com.adedom.utility

import android.content.Context
import android.location.Geocoder
import android.widget.ArrayAdapter

const val DATE_BEGIN = "0000-00-00"
const val TIME_BEGIN = "00:00"

val DATE_NOW = getDateTime(DATE)
val TIME_NOW = getDateTime(TIME)

var search = ""
var spinnerLevel = 0
var isCheckOnline = true
var isCheckOffline = true
var dateBegin = DATE_BEGIN
var timeBegin = TIME_BEGIN
var dateEnd = DATE_NOW
var timeEnd = TIME_NOW

fun Context.getLocality(latitude: Double, longitude: Double): String {
    val list = Geocoder(this).getFromLocation(latitude, longitude, 1)
    return if (list[0].locality != null) {
        list[0].locality
    } else {
        "unknown"
    }
}

fun Context.spinnerLevel(): ArrayAdapter<Int> {
    val list = ArrayList<Int>()
    for (i in 0..99) list.add(i)
    val adapter = ArrayAdapter<Int>(
        this, android.R.layout.simple_spinner_item, list
    )
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    return adapter
}
