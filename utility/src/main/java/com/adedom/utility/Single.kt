package com.adedom.utility

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*

enum class GameSwitch {
    ON, OFF
}

const val CAMERA_ZOOM = 15F
const val RADIUS_ONE_HUNDRED_METER = 100.0
const val TWO_KILOMETER = 2000.0F
const val ONE_HUNDRED_METER = 100.0F
const val TWO_HUNDRED_METER = 200.0
const val ITEM_MIN = 5
const val MAX_ITEM = 10
const val FIFTEEN_MINUTE = 900L

var myLocation: Marker? = null
var myCircle: Circle? = null
var switch = GameSwitch.ON

fun setCamera(googleMap: GoogleMap?, latLng: LatLng) {
    if (switch == GameSwitch.ON) {
        switch = GameSwitch.OFF
        val update = CameraUpdateFactory.newLatLngZoom(latLng, CAMERA_ZOOM)
        googleMap!!.animateCamera(update)
    }
}

fun removeMyMarker(marker: Marker?) = marker?.remove()

fun removeCircle(circle: Circle?) = circle?.remove()

fun removeItemMarker(myItem: ArrayList<Marker>) {
    for (marker in myItem) marker.remove()
    myItem.clear()
}

fun setMyMarker(
    googleMap: GoogleMap,
    latLng: LatLng,
    name: String,
    level: Int,
    icon: BitmapDescriptor
) {
    myLocation = googleMap.addMarker(
        MarkerOptions()
            .position(latLng)
            .icon(icon)
            .title(name)
            .snippet("Level : $level")
    )
}

fun setMyCircle(googleMap: GoogleMap?, latLng: LatLng) {
    myCircle = googleMap!!.addCircle(
        CircleOptions().center(latLng)
            .radius(RADIUS_ONE_HUNDRED_METER)
            .fillColor(R.color.colorWhite)
            .strokeColor(android.R.color.white)
            .strokeWidth(5f)
    )
}

fun setItemMarker(
    googleMap: GoogleMap?,
    latitude: Double,
    longitude: Double,
    itemId: Int,
    bmp: Bitmap?,
    myItem: ArrayList<Marker>
) {
    myItem.add(
        googleMap!!.addMarker(
            MarkerOptions().position(LatLng(latitude, longitude))
                .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                .title(titleItem(itemId))
        )
    )
}

fun rndLatLng(latLng: Double, meter: Double): Double {
    var rnd = Math.random() / 100 // < 0.01
    rnd += meter / 100000 // 200 Meter
    val s = String.format("%.7f", rnd)

    var ll: Double
    ll = if ((0..1).random() == 0) {
        latLng + s.toDouble()
    } else {
        latLng - s.toDouble()
    }

    ll = String.format("%.7f", ll).toDouble()
    return ll
}

fun titleItem(itemId: Int): String {
    var name = ""
    when (itemId) {
        1 -> name = "Experience point"
        2 -> name = "Mystery Box"
        3 -> name = "Mystery Item"
    }
    return name
}

fun imageMarker(context: Context, image: Int): Bitmap {
    return BitmapFactory.decodeResource(context.resources, image)
}

fun detailItem(itemId: Int, values: Int): String {
    var name = ""
    when (itemId) {
        1 -> name = "Experience point : $values"
        2 -> name = "Egg I" // egg false
        3 -> name = "Egg II" // hide egg + radius
        4 -> name = "Egg III" // stun
        5 -> name = "Egg IV" // angel
        6 -> name = "Egg V" // devil
    }
    return name
}