package com.adedom.theegggame.ui.single

import com.adedom.library.extension.addCircleOptions
import com.adedom.library.extension.removeCircle
import com.adedom.library.extension.removeMarker
import com.adedom.library.util.GoogleMapActivity.Companion.sGoogleMap
import com.adedom.library.util.GoogleMapActivity.Companion.sLatLng
import com.adedom.theegggame.ui.single.SingleActivityViewModel.Companion.circlePlayer
import com.adedom.theegggame.ui.single.SingleActivityViewModel.Companion.markerPlayer
import com.adedom.theegggame.util.CIRCLE_ONE_HUNDRED_METER

class Player {

    init {
        //remove
        markerPlayer?.removeMarker()
        circlePlayer?.removeCircle()

        //set
//        val player = MainActivity.sPlayer
//        setImageProfile(player.image, player.gender, {
//            markerPlayer = sGoogleMap!!.addMarkerOptions(
//                sLatLng,
//                BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
//                player.name!!,
//                sContext.resources.getString(R.string.level, player.level)
//            )
//        }, {
//            markerPlayer = sGoogleMap!!.addMarkerOptions(
//                sLatLng,
//                BitmapDescriptorFactory.fromResource(R.drawable.ic_player_female),
//                player.name!!,
//                sContext.resources.getString(R.string.level, player.level)
//            )
//        }, {
//            sContext.loadBitmap(imageUrl(player.image!!), {
//                markerPlayer = sGoogleMap!!.addMarkerOptions(
//                    sLatLng,
//                    BitmapDescriptorFactory.fromBitmap(it),
//                    player.name!!,
//                    sContext.resources.getString(R.string.level, player.level)
//                )
//            }, {
//                markerPlayer = sGoogleMap!!.addMarkerOptions(
//                    sLatLng,
//                    BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
//                    player.name!!,
//                    sContext.resources.getString(R.string.level, player.level)
//                )
//            })
//        })
        circlePlayer = sGoogleMap!!.addCircleOptions(sLatLng, CIRCLE_ONE_HUNDRED_METER)
    }

}
