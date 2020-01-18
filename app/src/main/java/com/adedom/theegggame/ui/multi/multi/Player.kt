package com.adedom.theegggame.ui.multi.multi

import com.adedom.library.data.KEY_EMPTY
import com.adedom.library.extension.*
import com.adedom.library.util.myCircle
import com.adedom.library.util.setCircle
import com.adedom.theegggame.R
import com.adedom.theegggame.data.imageUrl
import com.adedom.theegggame.data.models.RoomInfo
import com.adedom.theegggame.util.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng

class Player(items: ArrayList<RoomInfo>) {

    init {
        markerPlayers.removeMarkers()
        items.forEach { item ->
            val latLng = LatLng(item.latitude!!, item.longitude!!)

            //player
            when {
                item.image == KEY_EMPTY && item.gender == KEY_MALE -> {
                    markerPlayers.setMarkers(
                        GoogleMapActivity.sGoogleMap,
                        latLng,
                        BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                        item.name!!,
                        getLevel(item.level)
                    )
                }
                item.image == KEY_EMPTY && item.gender == KEY_FEMALE -> {
                    markerPlayers.setMarkers(
                        GoogleMapActivity.sGoogleMap,
                        latLng,
                        BitmapDescriptorFactory.fromResource(R.drawable.ic_player_female),
                        item.name!!,
                        getLevel(item.level)
                    )
                }
                item.image != KEY_EMPTY -> {
                    GoogleMapActivity.sContext.loadBitmap(imageUrl(item.image!!), {
                        markerPlayers.setMarkers(
                            GoogleMapActivity.sGoogleMap!!,
                            latLng,
                            BitmapDescriptorFactory.fromBitmap(it),
                            item.name,
                            getLevel(item.level)
                        )
                    }, {
                        markerPlayers.setMarkers(
                            GoogleMapActivity.sGoogleMap!!,
                            latLng,
                            BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                            item.name,
                            getLevel(item.level)
                        )
                    })
                }
            }

            //Circle
            if (GoogleMapActivity.sContext.getPrefFile(KEY_PLAYER_ID) == item.playerId) {
                myCircle?.removeCircle()
                setCircle(GoogleMapActivity.sGoogleMap, latLng, RADIUS_ONE_HUNDRED_METER)
            }
        }
    }
}
