package com.adedom.theegggame.ui.single

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.models.JsonResponse
import com.adedom.theegggame.data.repositories.SingleRepository

class SingleActivityViewModel(private val repository: SingleRepository) : ViewModel() {

    fun insertItem(
        playerId: String,
        itemId: Int,
        qty: Int,
        latitude: Double,
        longitude: Double,
        date: String,
        time: String
    ): LiveData<JsonResponse> {
        return repository.insertItem(playerId, itemId, qty, latitude, longitude, date, time)
    }
}
