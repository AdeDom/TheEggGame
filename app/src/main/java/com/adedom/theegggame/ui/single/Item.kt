package com.adedom.theegggame.ui.single

import android.graphics.Bitmap
import com.adedom.library.extension.removeMarkers
import com.adedom.library.extension.resourceBitmap
import com.adedom.library.extension.setMarkers
import com.adedom.library.util.GoogleMapActivity.Companion.sContext
import com.adedom.library.util.GoogleMapActivity.Companion.sGoogleMap
import com.adedom.library.util.KEY_EMPTY
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Single
import com.adedom.theegggame.util.KEY_BONUS
import com.adedom.theegggame.util.KEY_EXP
import com.adedom.theegggame.util.KEY_MYSTERY_BOX
import com.adedom.theegggame.util.KEY_MYSTERY_ITEM
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

class Item(singles: ArrayList<Single>, markerItems: ArrayList<Marker>) {

    init {
        markerItems.removeMarkers()
        singles.forEach {
            val (icon, title) = itemDetails(it.itemId)
            markerItems.setMarkers(
                sGoogleMap,
                LatLng(it.latitude, it.longitude),
                BitmapDescriptorFactory.fromBitmap(icon),
                title
            )
        }
    }

    private fun itemDetails(itemId: Int): Pair<Bitmap, String> {
        val bmp = when (itemId) {
            1 -> sContext.resourceBitmap(R.drawable.ic_egg)
            2 -> sContext.resourceBitmap(R.drawable.ic_mystery_box)
            3 -> sContext.resourceBitmap(R.drawable.ic_mystery_item)
            4 -> sContext.resourceBitmap(R.drawable.ic_egg_bonus)
            else -> sContext.resourceBitmap(R.drawable.ic_image_black)
        }

        val str = when (itemId) {
            1 -> KEY_EXP
            2 -> KEY_MYSTERY_BOX
            3 -> KEY_MYSTERY_ITEM
            4 -> KEY_BONUS
            else -> KEY_EMPTY
        }

        return Pair(bmp, str)
    }

}
