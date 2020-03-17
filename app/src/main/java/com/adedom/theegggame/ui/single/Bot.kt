package com.adedom.theegggame.ui.single

import com.adedom.library.extension.addMarkerOptions
import com.adedom.library.extension.removeMarker
import com.adedom.library.util.GoogleMapActivity.Companion.sContext
import com.adedom.library.util.GoogleMapActivity.Companion.sGoogleMap
import com.adedom.theegggame.R
import com.adedom.theegggame.ui.single.SingleActivityViewModel.Companion.markerBot
import com.adedom.theegggame.util.KEY_BOT
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng

//TODO find path gps location
class Bot(latLng: LatLng) {

    init {
        markerBot?.removeMarker()
        markerBot = sGoogleMap!!.addMarkerOptions(
            latLng,
            BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
            KEY_BOT.first,
            sContext.resources.getString(R.string.level, KEY_BOT.second)
        )
    }

}
