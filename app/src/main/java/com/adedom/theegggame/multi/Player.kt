package com.adedom.theegggame.multi

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.adedom.theegggame.MainActivity
import com.adedom.theegggame.R
import com.adedom.theegggame.model.RoomInfoItem
import com.adedom.theegggame.utility.MyIon
import com.adedom.theegggame.utility.MyMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class Player(item: ArrayList<RoomInfoItem>)  { // 21/7/62

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

    private fun setPeopleLocation(items: ArrayList<RoomInfoItem>) {
        for ((room_no, playerId, name, image, level, latitude, longitude) in items) {
            var bmp = BitmapFactory.decodeResource(MyMap.mContext.resources, R.drawable.ic_player)
            if (image.isNotEmpty()) {
                bmp = MyIon.getIon(MyMap.mContext, bmp, image)
                bmp = Bitmap.createScaledBitmap(bmp!!, (bmp.width * 0.2).toInt(), (bmp.height * 0.2).toInt(), true)
            }

            //player
            MultiActivity.mMarkerPlayer.add(
                MyMap.mGoogleMap!!.addMarker(
                    MarkerOptions()
                        .position(LatLng(latitude, longitude))
                        .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                        .title(name)
                        .snippet("Level : $level")
                )
            )

            //Circle
            val latLng = LatLng(latitude, longitude)
            if (MainActivity.mPlayerItem.id == playerId) {
                MultiActivity.mCircle = MyMap.mGoogleMap!!.addCircle(
                    CircleOptions()
                        .center(latLng)
                        .radius(Commons.ONE_HUNDRED_METER.toDouble())
                        .fillColor(R.color.colorRadius)
                        .strokeColor(R.color.colorStrokeRadius)
                        .strokeWidth(5f)
                )
            }
        }
    }
}