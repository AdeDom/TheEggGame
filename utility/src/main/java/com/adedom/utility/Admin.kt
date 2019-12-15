package com.adedom.utility

import android.content.Context
import android.location.Geocoder

fun Context.getLocality(latitude: Double, longitude: Double): String {
    val list = Geocoder(this).getFromLocation(latitude, longitude, 1)
    return if (list[0].locality != null) {
        list[0].locality
    } else {
        "unknown"
    }
}

interface OnAttachListener {
    fun onAttach(search:String)
}