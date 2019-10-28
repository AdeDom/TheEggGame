package com.adedom.theegggame.single

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.adedom.theegggame.R
import com.adedom.theegggame.model.SingleItem
import com.adedom.theegggame.utility.MyCode
import com.adedom.theegggame.utility.MyMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class Item(latLng: LatLng) { // 15/7/62

    init {
        if (SingleActivity.mSingleItem.size < Commons.NUMBER_OF_ITEM) {
            SingleActivity.mIsRndItem = true
        }

        if (SingleActivity.mIsRndItem) {
            SingleActivity.mIsRndItem = false
            rndItem(latLng)
        }
        removeMarkerItem()
        setMarker()
    }

    private fun rndItem(latLng: LatLng) {
        var numItem = (Math.random() * 10).toInt()
        while (numItem < Commons.NUMBER_OF_ITEM) {
            numItem = (Math.random() * 10).toInt()
        }

        for (i in 0 until numItem) {
            val item = SingleItem(
                controlItem(),
                MyCode.rndLatLng(latLng.latitude,Commons.TWO_HUNDRED_METER),
                MyCode.rndLatLng(latLng.longitude, Commons.TWO_HUNDRED_METER)
            )
            SingleActivity.mSingleItem.add(item)
        }
    }

    private fun controlItem(): Int {
        var num = (1..3).random()

        when (num) {
            2 -> {
                val mysteryBox = (1..3).random()
                num = if (mysteryBox == 2) {
                    mysteryBox
                } else {
                    1
                }
            }
            3 -> {
                val mysteryItem = (1..9).random()
                num = if (mysteryItem == 3) {
                    mysteryItem
                } else {
                    1
                }
            }
        }
        return num
    }

    private fun removeMarkerItem() {
        if (SingleActivity.mMarkerMyItem != null) {
            for (marker in SingleActivity.mMarkerMyItem) {
                marker.remove()
            }
            SingleActivity.mMarkerMyItem.clear()
        }
    }

    private fun setMarker() {
        var bmp: Bitmap? = null
        for ((itemId, latitude, longitude) in SingleActivity.mSingleItem) {
            when (itemId) {
                1 -> bmp = imageMarker(R.drawable.ic_egg)
                2 -> bmp = imageMarker(R.drawable.ic_mystery_box)
                3 -> bmp = imageMarker(R.drawable.ic_mystery_item)
            }

            SingleActivity.mMarkerMyItem.add(
                MyMap.mGoogleMap!!.addMarker(
                    MarkerOptions().position(LatLng(latitude, longitude))
                        .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                        .title(detailItem(itemId))
                )
            )
        }
    }

    private fun detailItem(itemId: Int): String {
        var name = ""
        when (itemId) {
            1 -> name = "Experience point"
            2 -> name = "Mystery Box"
            3 -> name = "Mystery Item"
        }
        return name
    }

    private fun imageMarker(image: Int): Bitmap {
        return BitmapFactory.decodeResource(MyMap.mContext.resources, image)
    }
}
