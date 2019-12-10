package com.adedom.theegggame.ui.single

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.adedom.theegggame.R
import com.adedom.theegggame.ui.main.MainActivity
import com.adedom.theegggame.util.MapActivity
import com.adedom.utility.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng

class Player(latLng: LatLng) {

    init {
        removeMarker(myLocation)
        removeCircle(myCircle)

        setMyLocation(latLng)
        setCircle(MapActivity.sGoogleMap, latLng)
    }

    private fun setMyLocation(latLng: LatLng) {
        val player = MainActivity.sPlayerItem

        if (player.image == EMPTY) {
            setMarker(
                MapActivity.sGoogleMap!!,
                latLng,
                BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                player.name!!,
                getLevel(player.level)
            )
        } else {
            Glide.with(MapActivity.sContext)
                .asBitmap()
                .load("$BASE_URL../profiles/${player.image}")
                .circleCrop()
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        setMarker(
                            MapActivity.sGoogleMap!!,
                            latLng,
                            BitmapDescriptorFactory.fromBitmap(resource),
                            player.name!!,
                            getLevel(player.level)
                        )
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        setMarker(
                            MapActivity.sGoogleMap!!,
                            latLng,
                            BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                            player.name!!,
                            getLevel(player.level)
                        )
                    }
                })
        }
    }

}