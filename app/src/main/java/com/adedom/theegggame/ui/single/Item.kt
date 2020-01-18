package com.adedom.theegggame.ui.single

import android.graphics.Bitmap
import com.adedom.library.extension.removeMarkers
import com.adedom.library.extension.resourceBitmap
import com.adedom.library.extension.setMarkers
import com.adedom.library.util.GoogleMapActivity
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Single
import com.adedom.theegggame.util.markerItems
import com.adedom.theegggame.util.titleItem
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng

class Item(singles: ArrayList<Single>) {

    init {
        markerItems.removeMarkers()
        var bmp: Bitmap? = null
        singles.forEach {
            when (it.itemId) {
                1 -> bmp = GoogleMapActivity.sContext.resourceBitmap(R.drawable.ic_egg)
                2 -> bmp = GoogleMapActivity.sContext.resourceBitmap(R.drawable.ic_mystery_box)
                3 -> bmp = GoogleMapActivity.sContext.resourceBitmap(R.drawable.ic_mystery_item)
            }

            markerItems.setMarkers(
                GoogleMapActivity.sGoogleMap,
                LatLng(it.latitude, it.longitude),
                BitmapDescriptorFactory.fromBitmap(bmp),
                titleItem(it.itemId)
            )
        }
    }

}
