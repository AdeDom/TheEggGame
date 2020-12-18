package com.adedom.theegggame.util

import android.widget.ImageView
import com.adedom.library.util.KEY_EMPTY
import com.adedom.library.util.getKeyUUID
import com.adedom.theegggame.R
import com.google.android.gms.maps.model.LatLng

var keyLogs: String = getKeyUUID()
var timeStamp: Long = System.currentTimeMillis() / 1000

fun setImageProfile(ivImage: ImageView, image: String, gender: String) {
    when {
        image == KEY_EMPTY && gender == KEY_MALE -> {
            ivImage.setImageResource(R.drawable.ic_player)
        }
        image == KEY_EMPTY && gender == KEY_FEMALE -> {
            ivImage.setImageResource(R.drawable.ic_player_female)
        }
//        image != KEY_EMPTY -> ivImage.loadCircle(imageUrl(image))
    }
}

fun setImageProfile(
    image: String?,
    gender: String?,
    male: () -> Unit,
    female: () -> Unit,
    loadImage: () -> Unit
) {
    when {
        image == KEY_EMPTY && gender == KEY_MALE -> male.invoke()
        image == KEY_EMPTY && gender == KEY_FEMALE -> female.invoke()
        image != KEY_EMPTY -> loadImage.invoke()
    }
}

fun rndLatLng(latLng: LatLng): Pair<Double, Double> {
    var rndLat = Math.random() / 100 // < 0.01
    rndLat += RADIUS_TWO_HUNDRED_METER / 100000 // 200 Meter
    val strLat = String.format(KEY_LATLNG, rndLat)
    val latitude: Double = if ((0..1).random() == 0) latLng.latitude + strLat.toDouble()
    else latLng.latitude - strLat.toDouble()

    var rndLng = Math.random() / 100 // < 0.01
    rndLng += RADIUS_TWO_HUNDRED_METER / 100000 // 200 Meter
    val strLng = String.format(KEY_LATLNG, rndLng)
    val longitude: Double = if ((0..1).random() == 0) latLng.longitude + strLng.toDouble()
    else latLng.longitude - strLng.toDouble()

    val lat = String.format(KEY_LATLNG, latitude).toDouble()
    val lng = String.format(KEY_LATLNG, longitude).toDouble()
    return Pair(lat, lng)
}