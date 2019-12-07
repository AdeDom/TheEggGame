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

class Player(items: List<RoomInfo>) { // 7/12/19

    init {
        removeListMarker(markerPlayers)
        removeCircle(myCircle)

        setPeopleLocation(items as ArrayList<RoomInfo>)
    }

    private fun setPeopleLocation(items: ArrayList<RoomInfo>) {
        for ((_, latitude, longitude, _, _, playerId, name, image, level, _) in items) {
            val latLng = LatLng(latitude!!, longitude!!)

            //player
            if (image == EMPTY) {
                setListMarker(
                    markerPlayers,
                    MapActivity.sGoogleMap,
                    latLng,
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                    name!!,
                    level!!.toString()
                )
            } else {
                Glide.with(MapActivity.sContext)
                    .asBitmap()
                    .load("$BASE_URL../profiles/${image}")
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
                                name!!,
                                level!!.toString()
                            )
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            setListMarker(
                                markerPlayers,
                                MapActivity.sGoogleMap,
                                latLng,
                                BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                                name!!,
                                level!!.toString()
                            )
                        }
                    })
            }

            //Circle
            if (MapActivity.sContext.getPrefLogin(PLAYER_ID) == playerId) {
                setCircle(MapActivity.sGoogleMap, latLng)
            }
        }
    }
}