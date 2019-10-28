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
        if (MainActivity.mPlayerItem.image.isNotEmpty()) {
            bmp = MyIon.getIon(MyMap.mContext, bmp, MainActivity.mPlayerItem.image)
            bmp = Bitmap.createScaledBitmap(bmp, (bmp.width * 0.2).toInt(), (bmp.height * 0.2).toInt(), true)
        }

        SingleActivity.mMarkerMyLocation = MyMap.mGoogleMap!!.addMarker(
            MarkerOptions().position(LatLng(latLng.latitude, latLng.longitude))
                .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                .title(MainActivity.mPlayerItem.name)
                .snippet("Level : " + MainActivity.mPlayerItem.level)
        )
    }

    private fun drawCircle(latLng: LatLng) {
        SingleActivity.mMyCircle = MyMap.mGoogleMap!!.addCircle(
            CircleOptions().center(latLng)
                .radius(Commons.ONE_HUNDRED_METER.toDouble())
                .fillColor(R.color.colorRadius)
                .strokeColor(R.color.colorStrokeRadius)
                .strokeWidth(5f)
        )
    }
}