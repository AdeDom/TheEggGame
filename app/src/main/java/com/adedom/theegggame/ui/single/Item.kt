package com.adedom.theegggame.ui.single

import android.graphics.Bitmap
import com.adedom.library.extension.removeMarkers
import com.adedom.library.extension.resourceBitmap
import com.adedom.library.extension.setMarkers
import com.adedom.library.util.GoogleMapActivity
import com.adedom.library.util.GoogleMapActivity.Companion.sGoogleMap
import com.adedom.library.util.KEY_EMPTY
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Single
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
            1 -> GoogleMapActivity.sContext.resourceBitmap(R.drawable.ic_egg)
            2 -> GoogleMapActivity.sContext.resourceBitmap(R.drawable.ic_mystery_box)
            3 -> GoogleMapActivity.sContext.resourceBitmap(R.drawable.ic_mystery_item)
            4 -> GoogleMapActivity.sContext.resourceBitmap(R.drawable.ic_egg_bonus)
            else -> GoogleMapActivity.sContext.resourceBitmap(R.drawable.ic_image_black)
        }

        val str = when (itemId) {
            1 -> "Experience point"
            2 -> "Mystery Box"
            3 -> "Mystery Item"
            4 -> "Bonus"
            else -> KEY_EMPTY
        }

        return Pair(bmp, str)
    }

}
