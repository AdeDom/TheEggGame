package com.adedom.theegggame.ui.multi.multi

import com.adedom.library.extension.removeMarkerList
import com.adedom.library.extension.setMarkerList
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Multi
import com.adedom.theegggame.util.MapActivity
import com.adedom.theegggame.util.markerItems
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng

class Item(multiple: ArrayList<Multi>) {

    init {
        markerItems.removeMarkerList()
        multiple.forEach {
            markerItems.setMarkerList(
                MapActivity.sGoogleMap,
                LatLng(it.latitude, it.longitude),
                BitmapDescriptorFactory.fromResource(R.drawable.the_egg_game)
            )
        }
    }
}