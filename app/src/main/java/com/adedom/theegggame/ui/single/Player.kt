package com.adedom.theegggame.ui.single

import com.adedom.theegggame.R
import com.adedom.theegggame.ui.main.MainActivity
import com.adedom.theegggame.util.MapActivity
import com.adedom.utility.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng

class Player(latLng: LatLng) {

    init {
        removeMarker(myLocation)
        removeCircle(myCircle)

        setMyLocation(latLng)
        setCircle(MapActivity.sGoogleMap, latLng)
    }

    private fun setMyLocation(latLng: LatLng) {
        val player = MainActivity.sPlayerItem
        if (player.image == EMPTY) {
            setMarker(
                MapActivity.sGoogleMap!!,
                latLng,
                BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                player.name!!,
                getLevel(player.level)
            )
        } else {
            loadBitmap(
                MapActivity.sContext,
                player.image!!,
                MapActivity.sGoogleMap!!,
                latLng,
                player.name!!,
                player.level!!
            )
        }
    }

}