package com.adedom.theegggame.single

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.adedom.theegggame.MainActivity
import com.adedom.theegggame.R
import com.adedom.theegggame.utility.MyIon
import com.adedom.theegggame.utility.MyMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class Player(latLng: LatLng)  { // 21/7/62

    init {
        removeMarker()
        setMyLocation(latLng)
        drawCircle(latLng)
    }

    private fun removeMarker() {
        if (SingleActivity.mMarkerMyLocation != null) {
            SingleActivity.mMarkerMyLocation!!.remove()
        }

        if (SingleActivity.mMyCircle != null) {
            SingleActivity.mMyCircle!!.remove()
        }
    }

    private fun setMyLocation(latLng: LatLng) {
        var bmp = BitmapFactory.decodeResource(MyMap.mContext.resources, R.drawable.ic_player)
        if (MainActivity.sPlayerItem.image!!.isNotEmpty()) {
            bmp = MyIon.getIon(MyMap.mContext, bmp, MainActivity.sPlayerItem.image!!)
            bmp = Bitmap.createScaledBitmap(bmp, (bmp.width * 0.2).toInt(), (bmp.height * 0.2).toInt(), true)
        }

        SingleActivity.mMarkerMyLocation = MyMap.mGoogleMap!!.addMarker(
            MarkerOptions().position(LatLng(latLng.latitude, latLng.longitude))
                .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                .title(MainActivity.sPlayerItem.name)
                .snippet("Level : " + MainActivity.sPlayerItem.level)
        )
    }

    private fun drawCircle(latLng: LatLng) {
        SingleActivity.mMyCircle = MyMap.mGoogleMap!!.addCircle(
            CircleOptions().center(latLng)
                .radius(Commons.ONE_HUNDRED_METER.toDouble())
                .fillColor(R.color.colorWhite)
                .strokeColor(android.R.color.white)
                .strokeWidth(5f)
        )
    }
}