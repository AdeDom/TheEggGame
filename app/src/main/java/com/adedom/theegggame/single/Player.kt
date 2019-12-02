package com.adedom.theegggame.single

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.adedom.theegggame.R
import com.adedom.theegggame.ui.activities.MainActivity
import com.adedom.theegggame.util.MapActivity
import com.adedom.utility.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng

class Player(latLng: LatLng) { // 2/12/19

    init {
        removeMyMarker(myLocation)
        removeCircle(myCircle)

        setMyLocation(latLng)
        setMyCircle(MapActivity.sGoogleMap, latLng)
    }

    private fun setMyLocation(latLng: LatLng) {
        val player = MainActivity.sPlayerItem

        if (player.image == "empty") {
            setMyMarker(
                MapActivity.sGoogleMap!!,
                latLng,
                player.name!!,
                player.level!!,
                BitmapDescriptorFactory.fromResource(R.drawable.ic_player)
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
                        setMyMarker(
                            MapActivity.sGoogleMap!!,
                            latLng,
                            player.name!!,
                            player.level!!,
                            BitmapDescriptorFactory.fromBitmap(resource)
                        )
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        setMyMarker(
                            MapActivity.sGoogleMap!!,
                            latLng,
                            player.name!!,
                            player.level!!,
                            BitmapDescriptorFactory.fromResource(R.drawable.ic_player)
                        )
                    }
                })
        }
    }

}