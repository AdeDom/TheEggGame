package com.adedom.theegggame.ui.single

import android.graphics.Bitmap
import com.adedom.theegggame.R
import com.adedom.theegggame.util.MapActivity
import com.adedom.utility.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng

class Item(singles: ArrayList<Single>) {

    init {
        removeListMarker(markerItems)
        var bmp: Bitmap? = null
        for ((itemId, latitude, longitude) in singles) {
            when (itemId) {
                1 -> bmp = MapActivity.sContext.imageMarker(R.drawable.ic_egg)
                2 -> bmp = MapActivity.sContext.imageMarker(R.drawable.ic_mystery_box)
                3 -> bmp = MapActivity.sContext.imageMarker(R.drawable.ic_mystery_item)
            }

            setListMarker(
                markerItems,
                MapActivity.sGoogleMap,
                LatLng(latitude, longitude),
                BitmapDescriptorFactory.fromBitmap(bmp),
                titleItem(itemId)
            )
        }
    }

}
