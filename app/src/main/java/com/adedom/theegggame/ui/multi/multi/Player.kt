package com.adedom.theegggame.ui.multi.multi

import com.adedom.library.extension.getPrefFile
import com.adedom.library.util.loadBitmap
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.RoomInfo
import com.adedom.theegggame.util.MapActivity
import com.adedom.theegggame.util.removeCircle
import com.adedom.theegggame.util.removeListMarker
import com.adedom.theegggame.util.setCircle
import com.adedom.utility.*
import com.adedom.utility.data.BASE_URL
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng

class Player(items: ArrayList<RoomInfo>) {

    init {
        removeListMarker(markerPlayers)

        items.forEach { item ->
            val latLng = LatLng(item.latitude!!, item.longitude!!)

            //player
            when {
                item.image == EMPTY && item.gender == MALE -> {
                    setListMarker(
                        markerPlayers,
                        MapActivity.sGoogleMap,
                        latLng,
                        BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                        item.name!!,
                        getLevel(item.level)
                    )
                }
                item.image == EMPTY && item.gender == FEMALE -> {
                    setListMarker(
                        markerPlayers,
                        MapActivity.sGoogleMap,
                        latLng,
                        BitmapDescriptorFactory.fromResource(R.drawable.ic_player_female),
                        item.name!!,
                        getLevel(item.level)
                    )
                }
                item.image != EMPTY -> {
                    loadBitmap(
                        MapActivity.sContext,
                        "$BASE_URL../profiles/${item.image}", {
                            setListMarker(
                                markerPlayers,
                                MapActivity.sGoogleMap!!,
                                latLng,
                                BitmapDescriptorFactory.fromBitmap(it),
                                item.name,
                                getLevel(item.level)
                            )
                        }, {
                            setListMarker(
                                markerPlayers,
                                MapActivity.sGoogleMap!!,
                                latLng,
                                BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                                item.name,
                                getLevel(item.level)
                            )
                        }
                    )
                }
            }

            //Circle
            if (MapActivity.sContext.getPrefFile(PLAYER_ID) == item.playerId) {
                removeCircle(myCircle)
                setCircle(MapActivity.sGoogleMap, latLng)
            }
        }
    }
}
