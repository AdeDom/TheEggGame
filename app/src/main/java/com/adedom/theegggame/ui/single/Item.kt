package com.adedom.theegggame.ui.single

import android.graphics.Bitmap
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Single
import com.adedom.theegggame.util.MapActivity
import com.adedom.utility.imageMarker
import com.adedom.utility.removeListMarker
import com.adedom.utility.setListMarker
import com.adedom.utility.titleItem
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

class Item(
    singles: ArrayList<Single>,
    mMarkerMyItem: ArrayList<Marker>
) { // 2/12/19

    init {
        removeListMarker(mMarkerMyItem)
        var bmp: Bitmap? = null
        for ((itemId, latitude, longitude) in singles) {
            when (itemId) {
                1 -> bmp = imageMarker(MapActivity.sContext, R.drawable.ic_egg)
                2 -> bmp = imageMarker(MapActivity.sContext, R.drawable.ic_mystery_box)
                3 -> bmp = imageMarker(MapActivity.sContext, R.drawable.ic_mystery_item)
            }

            setListMarker(
                mMarkerMyItem,
                MapActivity.sGoogleMap,
                LatLng(latitude, longitude),
                BitmapDescriptorFactory.fromBitmap(bmp),
                titleItem(itemId)
            )
        }
    }

}
