package com.adedom.theegggame.ui.multi.multi

import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Multi
import com.adedom.theegggame.util.MapActivity
import com.adedom.utility.markerItems
import com.adedom.utility.removeListMarker
import com.adedom.utility.setListMarker
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng

class Item(multiple: ArrayList<Multi>) {

    init {
        removeListMarker(markerItems)
        multiple.forEach {
            setListMarker(
                markerItems,
                MapActivity.sGoogleMap,
                LatLng(it.latitude, it.longitude),
                BitmapDescriptorFactory.fromResource(R.drawable.the_egg_game)
            )
        }
    }
}