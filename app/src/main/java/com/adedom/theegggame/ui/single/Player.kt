package com.adedom.theegggame.ui.single

import com.adedom.library.extension.*
import com.adedom.library.util.GoogleMapActivity.Companion.sContext
import com.adedom.library.util.GoogleMapActivity.Companion.sGoogleMap
import com.adedom.library.util.GoogleMapActivity.Companion.sLatLng
import com.adedom.theegggame.R
import com.adedom.theegggame.data.imageUrl
import com.adedom.theegggame.ui.main.MainActivity
import com.adedom.theegggame.util.CIRCLE_ONE_HUNDRED_METER
import com.adedom.theegggame.util.getLevel
import com.adedom.theegggame.util.setImageProfile
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class Player {

    init {
        //remove
        myMarker?.removeMarker()
        myCircle?.removeCircle()

        //set
        val player = MainActivity.sPlayer
        setImageProfile(player.image, player.gender, {
            sGoogleMap!!.setMarker(
                sLatLng,
                BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                player.name!!,
                getLevel(player.level)
            )
        }, {
            sGoogleMap!!.setMarker(
                sLatLng,
                BitmapDescriptorFactory.fromResource(R.drawable.ic_player_female),
                player.name!!,
                getLevel(player.level)
            )
        }, {
            sContext.loadBitmap(imageUrl(player.image!!), {
                sGoogleMap!!.setMarker(
                    sLatLng,
                    BitmapDescriptorFactory.fromBitmap(it),
                    player.name!!,
                    getLevel(player.level)
                )
            }, {
                sGoogleMap!!.setMarker(
                    sLatLng,
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                    player.name!!,
                    getLevel(player.level)
                )
            })
        })
        sGoogleMap!!.setCircle(sLatLng, CIRCLE_ONE_HUNDRED_METER)
    }

}
