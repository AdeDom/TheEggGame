package com.adedom.utility

import android.location.Location
import com.adedom.utility.data.Single
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*

enum class GameSwitch {
    ON, OFF
}

const val CAMERA_ZOOM = 15F
const val CAMERA_ZOOM_MIN = 12F
const val RADIUS_ONE_HUNDRED_METER = 100.0
const val TWO_KILOMETER = 2000.0F
const val ONE_HUNDRED_METER = 100.0F
const val TWO_HUNDRED_METER = 200.0
const val NUMBER_OF_ITEM = 5
const val MIN_ITEM = 5
const val MAX_ITEM = 10
const val FIFTEEN_MINUTE = 900
const val THREE_KILOMETER = 3000.0F
const val LATLNG_ZERO = 0.0

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
        googleMap.setMinZoomPreference(CAMERA_ZOOM_MIN)
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

fun rndLatLng(latLng: Double): Double {
    var rnd = Math.random() / 100 // < 0.01
    rnd += TWO_HUNDRED_METER / 100000 // 200 Meter
    val s = String.format("%.7f", rnd)
    var ll: Double = if ((0..1).random() == 0) latLng + s.toDouble() else latLng - s.toDouble()
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

fun detailItem(itemId: Int, values: Int): String {
    var name = ""
    when (itemId) {
        1 -> name = "Experience point : $values"
        2 -> name = "Egg I" // egg false
        3 -> name = "Egg II" // hide egg + radius
        4 -> name = "Egg III" // stun
    }
    return name
}

fun rndMultiItem(latLng: LatLng) {
    if (single.size < MIN_ITEM) {
        val numItem = (MIN_ITEM..MAX_ITEM).random()
        for (i in 0 until numItem) {
            val item = Single(
                (1..3).random(),
                rndLatLng(latLng.latitude),
                rndLatLng(latLng.longitude)
            )
            single.add(item)
        }
    }
}

fun getItemValues(i: Int, timeStamp: Long): Pair<Int, Int> {
    var myItem = single[i].itemId // item Id
    var values = (Math.random() * 100).toInt() + 20 // number values && minimum 20

    val timeNow = System.currentTimeMillis() / 1000
    if (timeNow > timeStamp + FIFTEEN_MINUTE.toLong()) values *= 2 // Multiply 2

    when (myItem) {
        2 -> { // mystery box
            myItem = (1..2).random() // random exp and item*/
            values += (1..20).random() // mystery box + 20 point.
            if (myItem == 2) {
                myItem = (2..4).random() // random item
                values = 1
            }
        }
        3 -> { // item
            myItem = (2..4).random()
            values = 1
        }
    }
    return Pair(myItem, values)
}

fun ready(): String {
    ready = if (ready == "unready") "ready" else "unready"
    return ready
}

fun getLevel(level: Int?): String = "Level : $level"

fun checkRadius(latLng: LatLng, insertItem: (Int) -> Unit) {
    single.forEachIndexed { index, item ->
        val distance = FloatArray(1)
        Location.distanceBetween(
            latLng.latitude,
            latLng.longitude,
            item.latitude,
            item.longitude,
            distance
        )

        if (distance[0] < ONE_HUNDRED_METER) {
            insertItem.invoke(index)
            markerItems[index].remove()
            single.removeAt(index)
            switchItem = GameSwitch.ON
            return
        }

        if (distance[0] > TWO_KILOMETER) {
            switchItem = GameSwitch.ON
            single.removeAt(index)
            return
        }
    }
}

fun rndMultiItem(
    status: String,
    roomInfoSize: Int,
    multiSize: Int,
    head: () -> Unit,
    tail: () -> Unit
) {
    when {
        status == HEAD && switchItem == GameSwitch.ON && roomInfoSize != 0 -> {
            switchItem = GameSwitch.OFF
            for (i in 0 until NUMBER_OF_ITEM) head.invoke()
        }
        status == TAIL && switchItem == GameSwitch.ON && multiSize != 0 -> {
            switchItem = GameSwitch.OFF
            tail.invoke()
        }
    }
}

fun distanceOver(latLng1: LatLng, latLng2: LatLng, distance: Float, over: () -> Unit) {
    val d = FloatArray(1)
    Location.distanceBetween(
        latLng1.latitude, latLng1.longitude,
        latLng2.latitude, latLng2.longitude, d
    )

    if (d[0] > distance) over.invoke()
}
