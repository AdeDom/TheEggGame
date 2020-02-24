package com.adedom.theegggame.ui.single

import com.adedom.library.extension.addMarkerOptions
import com.adedom.library.extension.removeMarker
import com.adedom.library.util.GoogleMapActivity
import com.adedom.theegggame.R
import com.adedom.theegggame.ui.single.SingleActivityViewModel.Companion.markerBot
import com.adedom.theegggame.util.getLevel
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class Bot {

    init {
        markerBot?.removeMarker()
        markerBot = GoogleMapActivity.sGoogleMap!!.addMarkerOptions(
            GoogleMapActivity.sLatLng,
            BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
            "Bot",
            getLevel(99)
        )
    }

}
