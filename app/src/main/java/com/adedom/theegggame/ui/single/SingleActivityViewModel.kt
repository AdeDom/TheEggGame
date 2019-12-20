package com.adedom.theegggame.ui.single

import com.adedom.theegggame.util.BaseViewModel

class SingleActivityViewModel : BaseViewModel() {

    fun insertItemCollection(
        playerId: String,
        itemId: Int,
        qty: Int,
        latitude: Double,
        longitude: Double,
        date: String,
        time: String
    ) = singleRepository.insertItemCollection(
        playerId,
        itemId,
        qty,
        latitude,
        longitude,
        date,
        time
    )
}
