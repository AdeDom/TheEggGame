package com.adedom.teg.presentation.single

import android.graphics.Bitmap

data class SingleViewState(
    val peopleAll: Int = 0,
    val name: String = "",
    val level: Int = 0,
    val latLng: Latlng = Latlng(),
    val bitmap: Bitmap? = null,
    val loading: Boolean = false,
) {

    class Latlng(
        val latitude: Double = 0.0,
        val longitude: Double = 0.0,
    )

}
