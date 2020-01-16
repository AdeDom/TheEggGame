package com.adedom.theegggame.util

import android.location.Location
import android.widget.EditText
import android.widget.ImageView
import com.adedom.library.data.KEY_EMPTY
import com.adedom.library.extension.loadCircle
import com.adedom.theegggame.data.imageUrl
import com.adedom.theegggame.data.models.Single
import com.adedom.utility.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

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
const val FIFTEEN_MINUTE = 60
const val THREE_KILOMETER = 3000.0F

const val ROOM = "room"
const val HEAD = "head"
const val TAIL = "tail"
const val TEAM_A = "A"
const val TEAM_B = "B"
const val READY = "ready"
const val UNREADY = "unready"

var team: String = ""
var ready = "unready"

val single by lazy { arrayListOf<Single>() }
val markerPlayers by lazy { arrayListOf<Marker>() }
val markerItems by lazy { arrayListOf<Marker>() }

var switchCamera = GameSwitch.ON
var switchItem = GameSwitch.ON

var timeStamp: Long = 0
var rndkey: String = ""

fun checkPassword(editText1: EditText, editText2: EditText, error: String): Boolean {
    val edt1 = editText1.text.toString().trim()
    val edt2 = editText2.text.toString().trim()
    if (edt1 != edt2) {
        editText2.requestFocus()
        editText2.error = error
        return true
    }
    return false
}

fun setCamera(googleMap: GoogleMap?, latLng: LatLng) {
    if (switchCamera == GameSwitch.ON) {
        switchCamera = GameSwitch.OFF
        val update = CameraUpdateFactory.newLatLngZoom(latLng, CAMERA_ZOOM)
        googleMap!!.animateCamera(update)
        googleMap.setMinZoomPreference(CAMERA_ZOOM_MIN)
    }
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

fun setReady(): String {
    ready = if (ready == "unready") "ready" else "unready"
    return ready
}

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

fun titleItem(itemId: Int): String {
    var name = ""
    when (itemId) {
        1 -> name = "Experience point"
        2 -> name = "Mystery Box"
        3 -> name = "Mystery Item"
    }
    return name
}

fun rndLatLng(latLng: Double): Double {
    var rnd = Math.random() / 100 // < 0.01
    rnd += TWO_HUNDRED_METER / 100000 // 200 Meter
    val s = String.format("%.7f", rnd)
    var ll: Double = if ((0..1).random() == 0) latLng + s.toDouble() else latLng - s.toDouble()
    ll = String.format("%.7f", ll).toDouble()
    return ll
}

fun setImageProfile(ivImage: ImageView, image: String, gender: String) {
    when {
        image == KEY_EMPTY && gender == KEY_MALE -> {
            ivImage.setImageResource(R.drawable.ic_player)
        }
        image == KEY_EMPTY && gender == KEY_FEMALE -> {
            ivImage.setImageResource(R.drawable.ic_player_female)
        }
        image != KEY_EMPTY -> ivImage.loadCircle(imageUrl(image))
    }
}

fun getLevel(level: Int?): String = "Level : $level"

