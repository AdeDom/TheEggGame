package com.adedom.theegggame.single

import android.graphics.Bitmap
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.SingleItem
import com.adedom.theegggame.util.MapActivity
import com.adedom.utility.imageMarker
import com.adedom.utility.removeItemMarker
import com.adedom.utility.setItemMarker
import com.google.android.gms.maps.model.Marker

class Item(
    singles: ArrayList<SingleItem>,
    mMarkerMyItem: ArrayList<Marker>
) { // 2/12/19

    init {
        removeItemMarker(mMarkerMyItem)
        var bmp: Bitmap? = null
        for ((itemId, latitude, longitude) in singles) {
            when (itemId) {
                1 -> bmp = imageMarker(MapActivity.sContext, R.drawable.ic_egg)
                2 -> bmp = imageMarker(MapActivity.sContext, R.drawable.ic_mystery_box)
                3 -> bmp = imageMarker(MapActivity.sContext, R.drawable.ic_mystery_item)
            }

            setItemMarker(MapActivity.sGoogleMap, latitude, longitude, itemId, bmp, mMarkerMyItem)
        }
    }

}
