package com.adedom.theegggame.ui.single

import com.adedom.library.extension.loadBitmap
import com.adedom.library.extension.removeCircle
import com.adedom.library.extension.removeMarker
import com.adedom.library.util.*
import com.adedom.theegggame.R
import com.adedom.theegggame.data.imageUrl
import com.adedom.theegggame.ui.main.MainActivity
import com.adedom.theegggame.util.CIRCLE_ONE_HUNDRED_METER
import com.adedom.theegggame.util.getLevel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng

class Player(latLng: LatLng) {

    init {
        myLocation?.removeMarker()
        myCircle?.removeCircle()

        setMyLocation(latLng)
        setCircle(GoogleMapActivity.sGoogleMap, latLng, CIRCLE_ONE_HUNDRED_METER)
    }

    private fun setMyLocation(latLng: LatLng) {
        val player = MainActivity.sPlayer
        SingleActivityViewModel.setImageProfile(player.image,player.gender,{
            setMarker(
                GoogleMapActivity.sGoogleMap!!,
                latLng,
                BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                player.name!!,
                getLevel(player.level)
            )
        },{
            setMarker(
                GoogleMapActivity.sGoogleMap!!,
                latLng,
                BitmapDescriptorFactory.fromResource(R.drawable.ic_player_female),
                player.name!!,
                getLevel(player.level)
            )
        },{
            GoogleMapActivity.sContext.loadBitmap(imageUrl(player.image!!), {
                setMarker(
                    GoogleMapActivity.sGoogleMap!!,
                    latLng,
                    BitmapDescriptorFactory.fromBitmap(it),
                    player.name!!,
                    getLevel(player.level)
                )
            }, {
                setMarker(
                    GoogleMapActivity.sGoogleMap!!,
                    latLng,
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                    player.name!!,
                    getLevel(player.level)
                )
            })
        })
    }

}
