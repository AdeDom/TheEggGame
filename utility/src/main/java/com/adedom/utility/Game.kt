package com.adedom.utility

import com.adedom.utility.data.Single
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

var myLocation: Marker? = null
var myCircle: Circle? = null

val single by lazy { arrayListOf<Single>() }
val markerPlayers by lazy { arrayListOf<Marker>() }
val markerItems by lazy { arrayListOf<Marker>() }

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

fun getLevel(level: Int?): String = "Level : $level"
