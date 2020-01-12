package com.adedom.theegggame.ui.multi.multi

import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.RoomInfo
import com.adedom.theegggame.util.MapActivity
import com.adedom.utility.*
import com.adedom.utility.extension.getPrefLogin
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng

class Player(items: ArrayList<RoomInfo>) {

    init {
        removeListMarker(markerPlayers)

        items.forEach {
            val latLng = LatLng(it.latitude!!, it.longitude!!)

            //player
            if (it.image == EMPTY) {
                setListMarker(
                    markerPlayers,
                    MapActivity.sGoogleMap,
                    latLng,
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                    it.name!!,
                    getLevel(it.level)
                )
            } else {
                loadBitmapList(
                    MapActivity.sContext,
                    it.image!!,
                    MapActivity.sGoogleMap!!,
                    latLng,
                    it.name!!,
                    it.level!!
                )
            }

            //Circle
            if (MapActivity.sContext.getPrefLogin(PLAYER_ID) == it.playerId) {
                removeCircle(myCircle)
                setCircle(MapActivity.sGoogleMap, latLng)
            }
        }
    }
}
