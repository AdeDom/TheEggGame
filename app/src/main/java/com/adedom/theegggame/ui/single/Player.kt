package com.adedom.theegggame.ui.single

import com.adedom.library.extension.*
import com.adedom.library.util.GoogleMapActivity.Companion.sContext
import com.adedom.library.util.GoogleMapActivity.Companion.sGoogleMap
import com.adedom.library.util.GoogleMapActivity.Companion.sLatLng
import com.adedom.theegggame.R
import com.adedom.theegggame.data.imageUrl
import com.adedom.theegggame.ui.main.MainActivity
import com.adedom.theegggame.ui.single.SingleActivityViewModel.Companion.circlePlayer
import com.adedom.theegggame.ui.single.SingleActivityViewModel.Companion.markerPlayer
import com.adedom.theegggame.util.CIRCLE_ONE_HUNDRED_METER
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
                sContext.resources.getString(R.string.level, player.level)
            )
        }, {
            markerPlayer = sGoogleMap!!.addMarkerOptions(
                sLatLng,
                BitmapDescriptorFactory.fromResource(R.drawable.ic_player_female),
                player.name!!,
                sContext.resources.getString(R.string.level, player.level)
            )
        }, {
            sContext.loadBitmap(imageUrl(player.image!!), {
                markerPlayer = sGoogleMap!!.addMarkerOptions(
                    sLatLng,
                    BitmapDescriptorFactory.fromBitmap(it),
                    player.name!!,
                    sContext.resources.getString(R.string.level, player.level)
                )
            }, {
                markerPlayer = sGoogleMap!!.addMarkerOptions(
                    sLatLng,
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                    player.name!!,
                    sContext.resources.getString(R.string.level, player.level)
                )
            })
        })
        circlePlayer = sGoogleMap!!.addCircleOptions(sLatLng, CIRCLE_ONE_HUNDRED_METER)
    }

}
