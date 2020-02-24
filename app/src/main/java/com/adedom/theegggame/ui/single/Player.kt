package com.adedom.theegggame.ui.single

import com.adedom.library.extension.loadBitmap
import com.adedom.library.extension.removeCircle
import com.adedom.library.extension.removeMarker
import com.adedom.library.util.GoogleMapActivity.Companion.sContext
import com.adedom.library.util.GoogleMapActivity.Companion.sGoogleMap
import com.adedom.library.util.GoogleMapActivity.Companion.sLatLng
import com.adedom.theegggame.R
import com.adedom.theegggame.data.imageUrl
import com.adedom.theegggame.ui.main.MainActivity
import com.adedom.theegggame.ui.single.SingleActivityViewModel.Companion.circlePlayer
import com.adedom.theegggame.ui.single.SingleActivityViewModel.Companion.markerPlayer
import com.adedom.theegggame.util.CIRCLE_ONE_HUNDRED_METER
import com.adedom.theegggame.util.extension.addCircleOptions
import com.adedom.theegggame.util.extension.addMarkerOptions
import com.adedom.theegggame.util.getLevel
import com.adedom.theegggame.util.setImageProfile
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class Player {

    init {
        //remove
        markerPlayer?.removeMarker()
        circlePlayer?.removeCircle()

        //set
        val player = MainActivity.sPlayer
        setImageProfile(player.image, player.gender, {
            markerPlayer = sGoogleMap!!.addMarkerOptions(
                sLatLng,
                BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                player.name!!,
                getLevel(player.level)
            )
        }, {
            markerPlayer = sGoogleMap!!.addMarkerOptions(
                sLatLng,
                BitmapDescriptorFactory.fromResource(R.drawable.ic_player_female),
                player.name!!,
                getLevel(player.level)
            )
        }, {
            sContext.loadBitmap(imageUrl(player.image!!), {
                markerPlayer = sGoogleMap!!.addMarkerOptions(
                    sLatLng,
                    BitmapDescriptorFactory.fromBitmap(it),
                    player.name!!,
                    getLevel(player.level)
                )
            }, {
                markerPlayer = sGoogleMap!!.addMarkerOptions(
                    sLatLng,
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                    player.name!!,
                    getLevel(player.level)
                )
            })
        })
        circlePlayer = sGoogleMap!!.addCircleOptions(sLatLng, CIRCLE_ONE_HUNDRED_METER)
    }

}
