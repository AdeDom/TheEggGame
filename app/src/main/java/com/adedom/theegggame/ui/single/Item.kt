package com.adedom.theegggame.ui.single

import com.adedom.library.extension.removeMarkers
import com.adedom.library.extension.setMarkers
import com.adedom.theegggame.data.models.Single
import com.adedom.theegggame.util.getItemBitmap
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

}
