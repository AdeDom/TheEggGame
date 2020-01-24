package com.adedom.theegggame.ui.single

import android.content.Context
import com.adedom.library.extension.*
import com.adedom.theegggame.R
import com.adedom.theegggame.data.imageUrl
import com.adedom.theegggame.ui.main.MainActivity
import com.adedom.theegggame.util.CIRCLE_ONE_HUNDRED_METER
import com.adedom.theegggame.util.getLevel
import com.adedom.theegggame.util.setImageProfile
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng

class Player(private val context: Context, private val googleMap: GoogleMap?, latLng: LatLng) {

    init {
        myMarker?.removeMarker()
        myCircle?.removeCircle()

        setMyLocation(latLng)
        googleMap!!.setCircle(latLng, CIRCLE_ONE_HUNDRED_METER)
    }

    private fun setMyLocation(latLng: LatLng) {
        val player = MainActivity.sPlayer
        setImageProfile(player.image, player.gender, {
            googleMap!!.setMarker(
                latLng,
                BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                player.name!!,
                getLevel(player.level)
            )
        }, {
            googleMap!!.setMarker(
                latLng,
                BitmapDescriptorFactory.fromResource(R.drawable.ic_player_female),
                player.name!!,
                getLevel(player.level)
            )
        }, {
            context.loadBitmap(imageUrl(player.image!!), {
                googleMap!!.setMarker(
                    latLng,
                    BitmapDescriptorFactory.fromBitmap(it),
                    player.name!!,
                    getLevel(player.level)
                )
            }, {
                googleMap!!.setMarker(
                    latLng,
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                    player.name!!,
                    getLevel(player.level)
                )
            })
        })
    }

}
