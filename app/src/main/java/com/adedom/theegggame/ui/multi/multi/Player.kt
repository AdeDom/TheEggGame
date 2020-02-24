package com.adedom.theegggame.ui.multi.multi

import com.adedom.library.extension.*
import com.adedom.library.util.GoogleMapActivity.Companion.sContext
import com.adedom.library.util.GoogleMapActivity.Companion.sGoogleMap
import com.adedom.theegggame.R
import com.adedom.theegggame.data.imageUrl
import com.adedom.theegggame.data.models.RoomInfo
import com.adedom.theegggame.ui.multi.multi.MultiActivityViewModel.Companion.circlePlayer
import com.adedom.theegggame.util.CIRCLE_ONE_HUNDRED_METER
import com.adedom.theegggame.util.KEY_PLAYER_ID
import com.adedom.theegggame.util.extension.addCircleOptions
import com.adedom.theegggame.util.getLevel
import com.adedom.theegggame.util.setImageProfile
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

class Player(
    items: ArrayList<RoomInfo>,
    markerPlayers: ArrayList<Marker>
) {

    init {
        markerPlayers.removeMarkers()
        items.forEach { item ->
            val latLng = LatLng(item.latitude!!, item.longitude!!)

            //player
            setImageProfile(item.image, item.gender, {
                markerPlayers.setMarkers(
                    sGoogleMap,
                    latLng,
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                    item.name!!,
                    getLevel(item.level)
                )
            }, {
                markerPlayers.setMarkers(
                    sGoogleMap,
                    latLng,
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_player_female),
                    item.name!!,
                    getLevel(item.level)
                )
            }, {
                sContext.loadBitmap(imageUrl(item.image!!), {
                    markerPlayers.setMarkers(
                        sGoogleMap,
                        latLng,
                        BitmapDescriptorFactory.fromBitmap(it),
                        item.name,
                        getLevel(item.level)
                    )
                }, {
                    markerPlayers.setMarkers(
                        sGoogleMap,
                        latLng,
                        BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                        item.name,
                        getLevel(item.level)
                    )
                })
            })

            //Circle
            if (sContext.readPrefFile(KEY_PLAYER_ID) == item.playerId) {
                circlePlayer?.removeCircle()
                circlePlayer = sGoogleMap!!.addCircleOptions(latLng, CIRCLE_ONE_HUNDRED_METER)
            }
        }
    }
}
