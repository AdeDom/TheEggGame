package com.adedom.theegggame.ui.multi.multi

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.RoomInfo
import com.adedom.theegggame.util.MapActivity
import com.adedom.utility.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng

class Player(items: ArrayList<RoomInfo>) {

    init {
        removeListMarker(markerPlayers)
        removeCircle(myCircle)

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
                Glide.with(MapActivity.sContext)
                    .asBitmap()
                    .load("$BASE_URL../profiles/${it.image}")
                    .circleCrop()
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            setListMarker(
                                markerPlayers,
                                MapActivity.sGoogleMap,
                                latLng,
                                BitmapDescriptorFactory.fromBitmap(resource),
                                it.name!!,
                                getLevel(it.level)
                            )
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            setListMarker(
                                markerPlayers,
                                MapActivity.sGoogleMap,
                                latLng,
                                BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                                it.name!!,
                                getLevel(it.level)
                            )
                        }
                    })
            }

            //Circle
            if (MapActivity.sContext.getPrefLogin(PLAYER_ID) == it.playerId) {
                setCircle(MapActivity.sGoogleMap, latLng)
            }
        }
    }
}