package com.adedom.theegggame.ui.multi.multi

import com.adedom.library.extension.removeMarkers
import com.adedom.library.extension.setMarkers
import com.adedom.library.util.GoogleMapActivity.Companion.sGoogleMap
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Multi
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

class Item(multiple: ArrayList<Multi>, markerItems: ArrayList<Marker>) {

    init {
        markerItems.removeMarkers()
        multiple.forEach {
            markerItems.setMarkers(
                sGoogleMap,
                LatLng(it.latitude, it.longitude),
                BitmapDescriptorFactory.fromResource(R.drawable.the_egg_game)
            )
        }
    }
}
