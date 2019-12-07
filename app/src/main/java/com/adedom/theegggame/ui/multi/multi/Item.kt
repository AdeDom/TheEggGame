package com.adedom.theegggame.ui.multi.multi

import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Multi
import com.adedom.theegggame.util.MapActivity
import com.adedom.utility.markerItems
import com.adedom.utility.removeListMarker
import com.adedom.utility.setListMarker
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng

class Item(multiple: List<Multi>) { // 7/12/19

    init {
        removeListMarker(markerItems)
        setItems(multiple as ArrayList<Multi>)
    }

    private fun setItems(multiple: ArrayList<Multi>) {
        for (multi in multiple) {
            setListMarker(
                markerItems,
                MapActivity.sGoogleMap,
                LatLng(multi.latitude, multi.longitude),
                BitmapDescriptorFactory.fromResource(R.drawable.the_egg_game)
            )
        }
    }
}