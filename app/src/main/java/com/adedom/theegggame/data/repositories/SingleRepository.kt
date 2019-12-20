package com.adedom.theegggame.data.repositories

import com.adedom.theegggame.data.networks.SingleApi
import com.adedom.utility.data.ApiRequest

class SingleRepository(private val api: SingleApi) : ApiRequest() {

    fun insertItemCollection(
        playerId: String,
        itemId: Int,
        qty: Int,
        latitude: Double,
        longitude: Double,
        date: String,
        time: String
    ) = apiRequest {
        api.insertItemCollection(
            playerId,
            itemId,
            qty,
            latitude,
            longitude,
            date,
            time
        )
    }

}