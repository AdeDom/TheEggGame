package com.adedom.theegggame.ui.single

import com.adedom.library.extension.*
import com.adedom.library.util.GoogleMapActivity
import com.adedom.theegggame.R
import com.adedom.theegggame.data.imageUrl
import com.adedom.theegggame.ui.main.MainActivity
import com.adedom.theegggame.util.CIRCLE_ONE_HUNDRED_METER
import com.adedom.theegggame.util.getLevel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng

class Player(latLng: LatLng) {

    init {
        myMarker?.removeMarker()
        myCircle?.removeCircle()

        setMyLocation(latLng)
        GoogleMapActivity.sGoogleMap!!.setCircle(latLng, CIRCLE_ONE_HUNDRED_METER)
    }

    private fun setMyLocation(latLng: LatLng) {
        val player = MainActivity.sPlayer
        SingleActivityViewModel.setImageProfile(player.image, player.gender, {
            GoogleMapActivity.sGoogleMap!!.setMarker(
                latLng,
                BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                player.name!!,
                getLevel(player.level)
            )
        }, {
            GoogleMapActivity.sGoogleMap!!.setMarker(
                latLng,
                BitmapDescriptorFactory.fromResource(R.drawable.ic_player_female),
                player.name!!,
                getLevel(player.level)
            )
        }, {
            GoogleMapActivity.sContext.loadBitmap(imageUrl(player.image!!), {
                GoogleMapActivity.sGoogleMap!!.setMarker(
                    latLng,
                    BitmapDescriptorFactory.fromBitmap(it),
                    player.name!!,
                    getLevel(player.level)
                )
            }, {
                GoogleMapActivity.sGoogleMap!!.setMarker(
                    latLng,
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                    player.name!!,
                    getLevel(player.level)
                )
            })
        })
    }

}
