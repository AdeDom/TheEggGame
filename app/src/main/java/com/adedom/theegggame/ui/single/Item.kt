package com.adedom.theegggame.ui.single

import com.adedom.library.extension.removeMarkers
import com.adedom.library.extension.setMarkers
import com.adedom.library.util.GoogleMapActivity
import com.adedom.theegggame.data.models.Single
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng

class Item(singles: ArrayList<Single>) {

    init {
        SingleActivityViewModel.markerItems.removeMarkers()
        singles.forEach {
            SingleActivityViewModel.markerItems.setMarkers(
                GoogleMapActivity.sGoogleMap,
                LatLng(it.latitude, it.longitude),
                BitmapDescriptorFactory.fromBitmap(SingleActivityViewModel.getItemBitmap(it.itemId)),
                SingleActivityViewModel().titleItem(it.itemId)
            )
        }
    }

}
