package com.adedom.theegggame.ui.single

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.models.JsonResponse
import com.adedom.theegggame.data.repositories.SingleRepository

class SingleActivityViewModel(private val repository: SingleRepository) : ViewModel() { // 2/12/19

    fun insertItem(playerId: String, itemId: Int, qty: Int): LiveData<JsonResponse> {
        return repository.insertItem(playerId, itemId, qty)
    }

}
