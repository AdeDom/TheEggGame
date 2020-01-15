package com.adedom.theegggame.ui.single

import android.graphics.Bitmap
import com.adedom.library.extension.resourceBitmap
import com.adedom.theegggame.util.MapActivity
import com.adedom.theegggame.util.removeListMarker
import com.adedom.theegggame.util.titleItem
import com.adedom.utility.R
import com.adedom.utility.data.Single
import com.adedom.utility.markerItems
import com.adedom.utility.setListMarker
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng

class Item(singles: ArrayList<Single>) {

    init {
        removeListMarker(markerItems)
        var bmp: Bitmap? = null
        singles.forEach {
            when (it.itemId) {
                1 -> bmp = MapActivity.sContext.resourceBitmap(R.drawable.ic_egg)
                2 -> bmp = MapActivity.sContext.resourceBitmap(R.drawable.ic_mystery_box)
                3 -> bmp = MapActivity.sContext.resourceBitmap(R.drawable.ic_mystery_item)
            }

            setListMarker(
                markerItems,
                MapActivity.sGoogleMap,
                LatLng(it.latitude, it.longitude),
                BitmapDescriptorFactory.fromBitmap(bmp),
                titleItem(it.itemId)
            )
        }
    }

}
