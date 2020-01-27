package com.adedom.theegggame.ui.single

import android.graphics.Bitmap
import com.adedom.library.extension.removeMarkers
import com.adedom.library.extension.resourceBitmap
import com.adedom.library.extension.setMarkers
import com.adedom.library.util.GoogleMapActivity
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Single
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

class Item(googleMap: GoogleMap?, singles: ArrayList<Single>, markerItems: ArrayList<Marker>) {

    init {
        markerItems.removeMarkers()
        singles.forEach {
            markerItems.setMarkers(
                googleMap,
                LatLng(it.latitude, it.longitude),
                BitmapDescriptorFactory.fromBitmap(getItemBitmap(it.itemId)),
                SingleActivityViewModel().titleItem(it.itemId)
            )
        }
    }

    private fun getItemBitmap(itemId: Int): Bitmap {
        return when (itemId) {
            1 -> GoogleMapActivity.sContext.resourceBitmap(R.drawable.ic_egg)
            2 -> GoogleMapActivity.sContext.resourceBitmap(R.drawable.ic_mystery_box)
            3 -> GoogleMapActivity.sContext.resourceBitmap(R.drawable.ic_mystery_item)
            4 -> GoogleMapActivity.sContext.resourceBitmap(R.drawable.ic_egg_bonus)
            else -> GoogleMapActivity.sContext.resourceBitmap(R.drawable.ic_image_black)
        }
    }

}
