package com.adedom.theegggame.ui.single

import com.adedom.library.util.loadBitmap
import com.adedom.theegggame.R
import com.adedom.theegggame.ui.main.MainActivity
import com.adedom.theegggame.util.MapActivity
import com.adedom.theegggame.util.removeCircle
import com.adedom.theegggame.util.removeMarker
import com.adedom.theegggame.util.setCircle
import com.adedom.utility.*
import com.adedom.utility.data.BASE_URL
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
        val player = MainActivity.sPlayer
        when {
            player.image == EMPTY && player.gender == MALE -> {
                setMarker(
                    MapActivity.sGoogleMap!!,
                    latLng,
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                    player.name!!,
                    getLevel(player.level)
                )
            }
            player.image == EMPTY && player.gender == FEMALE -> {
                setMarker(
                    MapActivity.sGoogleMap!!,
                    latLng,
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_player_female),
                    player.name!!,
                    getLevel(player.level)
                )
            }
            player.image != EMPTY -> {
                loadBitmap(
                    MapActivity.sContext,
                    "$BASE_URL../profiles/${player.image}", {
                        setMarker(
                            MapActivity.sGoogleMap!!,
                            latLng,
                            BitmapDescriptorFactory.fromBitmap(it),
                            player.name!!,
                            getLevel(player.level)
                        )
                    }, {
                        setMarker(
                            MapActivity.sGoogleMap!!,
                            latLng,
                            BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                            player.name!!,
                            getLevel(player.level)
                        )
                    }
                )
            }
        }
    }

}