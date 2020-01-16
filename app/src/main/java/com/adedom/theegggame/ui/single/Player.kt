package com.adedom.theegggame.ui.single

import com.adedom.library.data.KEY_EMPTY
import com.adedom.library.extension.loadBitmap
import com.adedom.library.extension.removeCircle
import com.adedom.library.extension.removeMarker
import com.adedom.library.util.myCircle
import com.adedom.library.util.myLocation
import com.adedom.library.util.setCircle
import com.adedom.library.util.setMarker
import com.adedom.theegggame.R
import com.adedom.theegggame.data.imageUrl
import com.adedom.theegggame.ui.main.MainActivity
import com.adedom.theegggame.util.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng

class Player(latLng: LatLng) {

    init {
        myLocation?.removeMarker()
        myCircle?.removeCircle()

        setMyLocation(latLng)
        setCircle(MapActivity.sGoogleMap, latLng, RADIUS_ONE_HUNDRED_METER)
    }

    private fun setMyLocation(latLng: LatLng) {
        val player = MainActivity.sPlayer
        when {
            player.image == KEY_EMPTY && player.gender == KEY_MALE -> {
                setMarker(
                    MapActivity.sGoogleMap!!,
                    latLng,
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                    player.name!!,
                    getLevel(player.level)
                )
            }
            player.image == KEY_EMPTY && player.gender == KEY_FEMALE -> {
                setMarker(
                    MapActivity.sGoogleMap!!,
                    latLng,
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_player_female),
                    player.name!!,
                    getLevel(player.level)
                )
            }
            player.image != KEY_EMPTY -> {
                MapActivity.sContext.loadBitmap(imageUrl(player.image!!), {
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
                })
            }
        }
    }

}
