package com.adedom.theegggame.data.repositories

import com.adedom.theegggame.data.networks.SingleApi
import com.adedom.theegggame.util.ApiRequest

class SingleRepository(private val api: SingleApi) : ApiRequest() {

    fun insertItem(
        playerId: String,
        itemId: Int,
        qty: Int,
        latitude: Double,
        longitude: Double,
        date: String,
        time: String
    ) = apiRequest { api.insertItem(playerId, itemId, qty, latitude, longitude, date, time) }

}