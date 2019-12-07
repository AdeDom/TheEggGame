package com.adedom.theegggame.ui.multi.multi

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.RoomInfo
import com.adedom.theegggame.ui.main.MainActivity
import com.adedom.theegggame.util.MapActivity
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class Player(item: ArrayList<RoomInfo>) { // 21/7/62

    init {
        removeMarker()
        setPeopleLocation(item)
    }

    private fun removeMarker() {
        if (MultiActivity.mMarkerPlayer != null) {
            for (marker in MultiActivity.mMarkerPlayer) {
                marker.remove()
            }
            MultiActivity.mMarkerPlayer.clear()
        }

        if (MultiActivity.mCircle != null) {
            MultiActivity.mCircle!!.remove()
        }
    }

    private fun setPeopleLocation(items: ArrayList<RoomInfo>) {
        for ((room_no, latitude, longitude, team, playerId, name, image, level, status) in items) {
            var bmp =
                BitmapFactory.decodeResource(MapActivity.sContext.resources, R.drawable.ic_player)
            if (image!!.isNotEmpty()) {
//                bmp = MyIon.getIon(MapActivity.sContext, bmp, image!!)
                bmp = Bitmap.createScaledBitmap(
                    bmp,
                    (bmp.width * 0.2).toInt(),
                    (bmp.height * 0.2).toInt(),
                    true
                )
            }

            //player
            MultiActivity.mMarkerPlayer.add(
                MapActivity.sGoogleMap!!.addMarker(
                    MarkerOptions()
                        .position(LatLng(latitude!!, longitude!!))
                        .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                        .title(name)
                        .snippet("Level : $level")
                )
            )

            //Circle
            val latLng = LatLng(latitude, longitude)
            if (MainActivity.sPlayerItem.playerId == playerId) {
                MultiActivity.mCircle = MapActivity.sGoogleMap!!.addCircle(
                    CircleOptions()
                        .center(latLng)
                        .radius(Commons.ONE_HUNDRED_METER.toDouble())
                        .fillColor(R.color.colorWhite)
                        .strokeColor(android.R.color.white)
                        .strokeWidth(5f)
                )
            }
        }
    }
}