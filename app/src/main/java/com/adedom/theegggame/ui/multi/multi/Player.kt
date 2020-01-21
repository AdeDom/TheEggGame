package com.adedom.theegggame.ui.multi.multi

import com.adedom.library.extension.*
import com.adedom.library.util.GoogleMapActivity
import com.adedom.theegggame.R
import com.adedom.theegggame.data.imageUrl
import com.adedom.theegggame.data.models.RoomInfo
import com.adedom.theegggame.util.CIRCLE_ONE_HUNDRED_METER
import com.adedom.theegggame.util.KEY_PLAYER_ID
import com.adedom.theegggame.util.getLevel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng

class Player(items: ArrayList<RoomInfo>) {

    init {
        MultiActivityViewModel.markerPlayers.removeMarkers()
        items.forEach { item ->
            val latLng = LatLng(item.latitude!!, item.longitude!!)

            //player
            MultiActivityViewModel.setImageProfile(item.image, item.gender, {
                MultiActivityViewModel.markerPlayers.setMarkers(
                    GoogleMapActivity.sGoogleMap,
                    latLng,
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                    item.name!!,
                    getLevel(item.level)
                )
            }, {
                MultiActivityViewModel.markerPlayers.setMarkers(
                    GoogleMapActivity.sGoogleMap,
                    latLng,
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_player_female),
                    item.name!!,
                    getLevel(item.level)
                )
            }, {
                GoogleMapActivity.sContext.loadBitmap(imageUrl(item.image!!), {
                    MultiActivityViewModel.markerPlayers.setMarkers(
                        GoogleMapActivity.sGoogleMap!!,
                        latLng,
                        BitmapDescriptorFactory.fromBitmap(it),
                        item.name,
                        getLevel(item.level)
                    )
                }, {
                    MultiActivityViewModel.markerPlayers.setMarkers(
                        GoogleMapActivity.sGoogleMap!!,
                        latLng,
                        BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                        item.name,
                        getLevel(item.level)
                    )
                })
            })

            //Circle
            if (GoogleMapActivity.sContext.readPrefFile(KEY_PLAYER_ID) == item.playerId) {
                myCircle?.removeCircle()
                GoogleMapActivity.sGoogleMap!!.setCircle( latLng, CIRCLE_ONE_HUNDRED_METER)
            }
        }
    }
}
