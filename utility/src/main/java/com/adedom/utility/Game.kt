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
const val ROOM = "room"
const val HEAD = "head"
const val TAIL = "tail"
const val TEAM_A = "A"
const val TEAM_B = "B"
const val READY = "ready"
const val UNREADY = "unready"

var ready = "unready"

var myLocation: Marker? = null
var myCircle: Circle? = null

val single by lazy { arrayListOf<Single>() }
val markerPlayers by lazy { arrayListOf<Marker>() }
val markerItems by lazy { arrayListOf<Marker>() }

var switchCamera = GameSwitch.ON
var switchItem = GameSwitch.ON

fun setCamera(googleMap: GoogleMap?, latLng: LatLng) {
    if (switchCamera == GameSwitch.ON) {
        switchCamera = GameSwitch.OFF
        val update = CameraUpdateFactory.newLatLngZoom(latLng, CAMERA_ZOOM)
        googleMap!!.animateCamera(update)
    }
}

fun removeMarker(marker: Marker?) = marker?.remove()

fun removeCircle(circle: Circle?) = circle?.remove()

fun removeListMarker(markers: ArrayList<Marker>) {
    for (marker in markers) marker.remove()
    markers.clear()
}

fun setMarker(
    googleMap: GoogleMap,
    latLng: LatLng,
    icon: BitmapDescriptor,
    title: String,
    snippet: String
) {
    myLocation = googleMap.addMarker(
        MarkerOptions()
            .position(latLng)
            .icon(icon)
            .title(title)
            .snippet(snippet)
    )
}

fun setCircle(googleMap: GoogleMap?, latLng: LatLng) {
    myCircle = googleMap!!.addCircle(
        CircleOptions().center(latLng)
            .radius(RADIUS_ONE_HUNDRED_METER)
            .fillColor(R.color.colorWhite)
            .strokeColor(android.R.color.white)
            .strokeWidth(5f)
    )
}

fun setListMarker(
    markers: ArrayList<Marker>,
    googleMap: GoogleMap?,
    latLng: LatLng,
    icon: BitmapDescriptor,
    title: String? = null,
    snippet: String? = null
) {
    markers.add(
        googleMap!!.addMarker(
            MarkerOptions().position(latLng)
                .icon(icon)
                .title(title)
                .snippet(snippet)
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

fun rndItem(latLng: LatLng) {
    if (single.size < ITEM_MIN) {
        val numItem = (ITEM_MIN..MAX_ITEM).random()
        for (i in 0 until numItem) {
            val item = Single(
                (1..3).random(),
                rndLatLng(latLng.latitude, TWO_HUNDRED_METER),
                rndLatLng(latLng.longitude, TWO_HUNDRED_METER)
            )
            single.add(item)
        }
    }
}

fun getItemValues(i: Int, timeStamp: Long): Pair<Int, Int> {
    var myItem = single[i].itemId // item Id
    var values = (Math.random() * 100).toInt() + 20 // number values && minimum 20

    val timeNow = System.currentTimeMillis() / 1000
    if (timeNow > timeStamp + FIFTEEN_MINUTE) values *= 2 // Multiply 2

    when (myItem) {
        2 -> { // mystery box
            myItem = (1..2).random() // random exp and item*/
            values += (1..20).random() // mystery box + 20 point.
            if (myItem == 2) {
                myItem = (2..6).random() // random item
                values = 1
            }
        }
        3 -> { // item
            myItem = (2..6).random()
            values = 1
        }
    }
    return Pair(myItem, values)
}

fun ready(): String {
    ready = if (ready == "unready") "ready" else "unready"
    return ready
}